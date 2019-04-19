package session

import akka.http.scaladsl.model.{ContentTypes, StatusCodes}
import akka.http.scaladsl.server.Route
import utils.{TemplateSpec, TestHelpers}
import io.circe.syntax._
import SessionSpecHelpers._
import user.UserSpecHelpers.testUserCreate

class SessionSpec extends TemplateSpec with TestHelpers {

  val userMock = testUserCreate
  val userUpdateMock = userService.createUser(userMock.toUser)
  val idUpdate = result(userUpdateMock)

  val sessionMock = testSessionCreate(idUpdate)

  "SessionSpec" should {
    "get session list" in {
      Get("/session") ~> Route.seal(routes) ~> check {
        status shouldBe StatusCodes.OK
      }
    }

    "get session" in {
      Get(s"/session/${idUpdate.id}") ~> Route.seal(routes) ~> check {
        status shouldBe StatusCodes.OK
      }
    }

    "post session" in {
      Post("/session").withEntity(ContentTypes.`application/json`, sessionMock.asJson.toString) ~>
        Route.seal(routes) ~> check {
        status shouldBe StatusCodes.Created
      }
    }


  }
}
