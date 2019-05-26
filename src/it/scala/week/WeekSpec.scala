package week

import akka.http.scaladsl.model.{ContentTypes, StatusCodes}
import akka.http.scaladsl.server.Route
import user.UserSpecHelpers.testUserCreate
import utils.{TemplateSpec, TestHelpers}
import io.circe.syntax._
import WeekSpecHelpers._
import akka.http.scaladsl.model.headers.Cookie

class WeekSpec extends TemplateSpec with TestHelpers {

  val userMock = userService.createUser(testUserCreate.toUser)
  val userIdMock = result(userMock)
  val weekDayMock = testWeekPut(userIdMock)

  "WeekSpec" should {
    "get user's week" in {
      Get(s"/user/${userIdMock.id}/week") ~>
        Cookie("sprinter-client" -> sessionId) ~>
        Route.seal(routes) ~> check {
        status shouldBe StatusCodes.OK
      }
    }

    "put user's week day declaration" in {
      Put(s"/user/${userIdMock.id}/week").withEntity(ContentTypes.`application/json`, weekDayMock.asJson.toString) ~>
        Cookie("sprinter-client" -> sessionId) ~>
        Route.seal(routes) ~> check {
        status shouldBe StatusCodes.NoContent
      }
    }
  }

}
