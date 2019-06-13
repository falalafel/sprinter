package sprint

import akka.http.scaladsl.model.{ContentTypes, StatusCodes}
import akka.http.scaladsl.server.Route
import io.circe.syntax._
import utils.{TemplateSpec, TestHelpers}
import SprintSpecHelpers._
import akka.http.scaladsl.model.headers.Cookie

class SprintSpec extends TemplateSpec with TestHelpers {

  val projectCreateQuery = projectService.createProject(projectCreate.toProject)
  val projectId = result(projectCreateQuery)

  val sprintCreateQuery = sprintService.createSprint(sprintCreate, projectId)
  val (_, sprintId) = result(sprintCreateQuery).get

  val sprintCreateMock = sprintCreate
  val sprintUpdateMock = sprintUpdate

  "SprintSpec" should {
    "get project's sprint list" in {
      Get(s"/project/${projectId.id}/sprint") ~>
        Cookie("sprinter-client" -> sessionId) ~>Route.seal(routes) ~> check {
        status shouldBe StatusCodes.OK
      }
    }

    "get sprint from project" in {
      Get(s"/project/${projectId.id}/sprint/${sprintId.id}") ~>
        Cookie("sprinter-client" -> sessionId) ~>Route.seal(routes) ~> check {
        status shouldBe StatusCodes.OK
      }
    }

    "post sprint to project" in {
      Post(s"/project/${projectId.id}/sprint").withEntity(ContentTypes.`application/json`, sprintCreateMock.asJson.toString) ~>
        Cookie("sprinter-client" -> sessionId) ~>
        Route.seal(routes) ~> check {
          status shouldBe StatusCodes.Created
      }
    }

    "update sprint" in {
      Patch(s"/project/${projectId.id}/sprint/${sprintId.id}").withEntity(ContentTypes.`application/json`, sprintUpdateMock.asJson.toString()) ~>
        Cookie("sprinter-client" -> sessionId) ~>
      Route.seal(routes) ~> check {
        status shouldBe StatusCodes.NoContent
      }
    }

    "delete sprint" in {
      Delete(s"/project/${projectId.id}/sprint/${sprintId.id}") ~>
        Cookie("sprinter-client" -> sessionId) ~> Route.seal(routes) ~> check {
        status shouldBe StatusCodes.NoContent
      }
    }

  }

}
