package project

import org.scalatest.{BeforeAndAfter, Matchers, WordSpec}
import akka.http.scaladsl.model.{ContentTypes, StatusCodes}
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.testkit.ScalatestRouteTest
import main.MainContext
import ProjectSpecHelpers._
import io.circe.syntax._
import project.domain.{ProjectClosingStatus, ProjectName}

class ProjectSpec extends WordSpec with Matchers with ScalatestRouteTest with MainContext with BeforeAndAfter {

  val pr = testProjectCreate
  val xd = pr.toProject
  val ss = testProjectUpdate(ProjectName("xdd"), ProjectClosingStatus(false))


  before {
    println(xd.projectId)

  }


  "ProjectSpec" should {

    "get project list" in {
      Get("/project") ~> Route.seal(routes) ~> check {
        status shouldBe StatusCodes.OK
      }
    }

    "post project" in {
      Post("/project").withEntity(ContentTypes.`application/json`, pr.asJson.toString) ~> Route.seal(routes) ~> check {
        status shouldBe StatusCodes.Created
      }
    }

    "update project" in {
      val id = result(projectService.createProject(pr.toProject))
      Patch(s"/project/${id.id}").withEntity(ContentTypes.`application/json`, ss.asJson.toString()) ~>
        Route.seal(routes) ~> check {
        status shouldBe StatusCodes.NoContent
      }
    }

    "delete project" in {
      Delete(s"/project/${xd.projectId.id}") ~> Route.seal(routes) ~> check {
        status shouldBe StatusCodes.NoContent
      }
    }

  }

}
