package user

import akka.http.scaladsl.model.{ContentTypes, StatusCodes}
import akka.http.scaladsl.server.Route
import utils.{TemplateSpec, TestHelpers}
import io.circe.syntax._
import UserSpecHelpers._
import akka.http.scaladsl.model.headers.Cookie

class UserSpec extends TemplateSpec with TestHelpers {

  val userMock = testUserCreate

  val userUpdateMock = userService.createUser(userMock.toUser)
  val idUpdate = result(userUpdateMock)
  val userUpdate = testUserUpdate()

  val userDeleteMock = userService.createUser(userMock.toUser)
  val idDelete = result(userDeleteMock)

  "UserSpec" should {
    "get user list" in {
      Get("/user") ~> Cookie("sprinter-client" -> sessionId) ~> Route.seal(routes) ~> check {
        status shouldBe StatusCodes.OK
      }
    }

    "post user" in {
      Post("/user").withEntity(ContentTypes.`application/json`, userMock.asJson.toString) ~>
        Cookie("sprinter-client" -> sessionId) ~>
        Route.seal(routes) ~> check {
        status shouldBe StatusCodes.Created
      }
    }

    "update user" in {
      Patch(s"/user/${idUpdate.id}").withEntity(ContentTypes.`application/json`, userUpdate.asJson.toString()) ~>
        Cookie("sprinter-client" -> sessionId) ~>
        Route.seal(routes) ~> check {
        status shouldBe StatusCodes.NoContent
      }
    }

    "delete user" in {
      Delete(s"/user/${idDelete.id}") ~>
        Cookie("sprinter-client" -> sessionId) ~>
        Route.seal(routes) ~> check {
        status shouldBe StatusCodes.NoContent
      }
    }
  }

}
