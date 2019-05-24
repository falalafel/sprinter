package session

import akka.http.scaladsl.model.{ContentTypes, StatusCodes}
import akka.http.scaladsl.server.Route
import utils.{TemplateSpec, TestHelpers}
import io.circe.syntax._
import SessionSpecHelpers._
import akka.http.scaladsl.model.headers.Cookie
import user.UserSpecHelpers.testUserCreate

class SessionSpec extends TemplateSpec with TestHelpers {

  val userMock = userService.createUser(testUserCreate.toUser)
  val userId = result(userMock)
  val sessionMock = testSessionCreate(userId)

  "SessionSpec" should {
    "get session list" in {
      Get(s"/user/${userId.id}/session") ~> Cookie("sprinter-client" -> sessionId) ~> Route.seal(routes) ~> check {
        status shouldBe StatusCodes.OK
      }
    }

    "post session" in {
      Post(s"/user/${userId.id}/session").withEntity(ContentTypes.`application/json`, sessionMock.asJson.toString) ~>
        Cookie("sprinter-client" -> sessionId) ~>
        Route.seal(routes) ~> check {
        status shouldBe StatusCodes.Created
      }
    }


  }
}
