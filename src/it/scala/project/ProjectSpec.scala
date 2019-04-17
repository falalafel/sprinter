package project

import org.scalatest.{Matchers, WordSpec}
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.testkit.ScalatestRouteTest
import main.MainContext

class ProjectSpec extends WordSpec with Matchers with ScalatestRouteTest with MainContext {

  "ProjectSpec" should {
    "get project list" in {
      Get("/project") ~> Route.seal(routes) ~> check {
        status shouldBe StatusCodes.OK
      }
    }

  }

}