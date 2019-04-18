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

  val projectMock = testProjectCreate

  val projectUpdateMock = projectService.createProject(projectMock.toProject)
  val idUpdate = result(projectUpdateMock)
  val projectUpdate = testProjectUpdate(ProjectName("afterUpdate"), ProjectClosingStatus(false))

  val projectDeleteMock = projectService.createProject(projectMock.toProject)
  val idDelete = result(projectDeleteMock)

  "ProjectSpec" should {
    "get project list" in {
      Get("/project") ~> Route.seal(routes) ~> check {
        status shouldBe StatusCodes.OK
      }
    }

    "post project" in {
      Post("/project").withEntity(ContentTypes.`application/json`, projectMock.asJson.toString) ~>
        Route.seal(routes) ~> check {
        status shouldBe StatusCodes.Created
      }
    }

    "update project" in {
      Patch(s"/project/${idUpdate.id}").withEntity(ContentTypes.`application/json`, projectUpdate.asJson.toString()) ~>
        Route.seal(routes) ~> check {
        status shouldBe StatusCodes.NoContent
      }
    }

    "delete project" in {
      Delete(s"/project/${idDelete.id}") ~> Route.seal(routes) ~> check {
        status shouldBe StatusCodes.NoContent
      }
    }

  }

}
