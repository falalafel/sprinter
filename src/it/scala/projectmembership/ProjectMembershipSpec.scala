package projectmembership

import akka.http.scaladsl.model.headers.Cookie
import akka.http.scaladsl.model.{ContentTypes, StatusCodes}
import akka.http.scaladsl.server.Route
import io.circe.syntax._
import projectmembership.ProjectMembershipHelpers._
import utils.{TemplateSpec, TestHelpers}

class ProjectMembershipSpec extends TemplateSpec with TestHelpers {

  val projectCreateQuery = projectService.createProject(projectCreate.toProject)
  val projectId = result(projectCreateQuery)

  val userCreateQuery = userService.createUser(userCreate.toUser)
  val userId = result(userCreateQuery)

  val projectMembershipCreateQuery = projectMembershipService.insertOrUpdateProjectMembership(
    projectMembershipCreate.toProjectMembership(projectId, userId))

  result(projectMembershipCreateQuery)

  "ProjectMembershipSpec" should {
    "get list of projectmemberships for project" in {
      Get(s"/project/${projectId.id}/membership") ~> Cookie("sprinter-client" -> sessionId) ~> Route.seal(routes) ~> check {
        status shouldBe StatusCodes.OK
      }
    }

    "get specified user's projectmembership for project" in {
      Get(s"/project/${projectId.id}/membership/${userId.id}") ~> Cookie("sprinter-client" -> sessionId) ~> Route.seal(routes) ~> check {
        status shouldBe StatusCodes.OK
      }
    }

    "put user's projectmemberships for project" in {
      Put(s"/project/${projectId.id}/membership/${userId.id}")
        .withEntity(ContentTypes.`application/json`, projectMembershipCreate.asJson.toString) ~>
        Cookie("sprinter-client" -> sessionId) ~>
        Route.seal(routes) ~> check {
        status shouldBe StatusCodes.NoContent
      }
    }

    "delete projectmembership" in {
      Delete(s"/project/${projectId.id}/membership/${userId.id}") ~>
        Cookie("sprinter-client" -> sessionId) ~> Route.seal(routes) ~> check {
        status shouldBe StatusCodes.NoContent
      }
    }

  }

}
