package declaration

import akka.http.scaladsl.model.{ContentTypes, StatusCodes}
import akka.http.scaladsl.server.Route
import io.circe.syntax._
import utils.{TemplateSpec, TestHelpers}
import DeclarationSpecHelpers._
import akka.http.scaladsl.model.headers.Cookie
import sprint.SprintSpecHelpers.sprintUpdate

class DeclarationSpec extends TemplateSpec with TestHelpers {

  val projectCreateQuery = projectService.createProject(projectCreate.toProject)
  val projectId = result(projectCreateQuery)

  val sprintCreateQuery1 = sprintService.createSprint(sprintCreate, projectId)
  val (_, sprintId1) = result(sprintCreateQuery1).get

  val userCreateQuery = userService.createUser(userCreate.toUser)
  val userId = result(userCreateQuery)

  val declarationCreateQuery = declarationService.insertOrUpdateDeclaration(
    declarationCreate.toDeclaration(projectId, sprintId1, userId))

  result(declarationCreateQuery)

  val sprintCloseQuery1 = sprintService.updateSprint(projectId, sprintId1, sprintUpdate)
  result(sprintCloseQuery1)

  val sprintCreateQuery2 = sprintService.createSprint(sprintCreate, projectId)
  val (_, sprintId2) = result(sprintCreateQuery2).get

  "DeclarationSpec" should {
    "get list of declarations for sprint" in {
      Get(s"/project/${projectId.id}/sprint/${sprintId1.id}/declaration") ~> Cookie("sprinter-client" -> sessionId) ~>
        Route.seal(routes) ~> check {
        status shouldBe StatusCodes.OK
      }
    }

    "get specified user's declaration for sprint" in {
      Get(s"/project/${projectId.id}/sprint/${sprintId1.id}/declaration/${userId.id}") ~> Cookie("sprinter-client" -> sessionId) ~>
        Route.seal(routes) ~> check {
        status shouldBe StatusCodes.OK
      }
    }

    "put user's declaration for sprint" in {
       Put(s"/project/${projectId.id}/sprint/${sprintId2.id}/declaration/${userId.id}")
        .withEntity(ContentTypes.`application/json`, declarationCreate.asJson.toString) ~> Cookie("sprinter-client" -> sessionId) ~>
        Route.seal(routes) ~> check {
        status shouldBe StatusCodes.NoContent
      }
    }

    "delete declaration" in {
      Delete(s"/project/${projectId.id}/sprint/${sprintId2.id}/declaration/${userId.id}") ~> Cookie("sprinter-client" -> sessionId) ~>
        Route.seal(routes) ~> check {
        status shouldBe StatusCodes.NoContent
      }
    }

  }

}
