package project

import org.scalatest.{Matchers, WordSpec}
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.http.scaladsl.server._
import com.typesafe.config.ConfigFactory
import project.routes.ProjectRoute
import project.services.ProjectService
import project.storages.ProjectStorage
import slick.jdbc.PostgresProfile.api._

class ProjectSpec extends WordSpec with Matchers with ScalatestRouteTest {

  val config = ConfigFactory.load()
  val configDbPath = "postgres"
  val database = Database.forConfig(configDbPath, config)
  val projectRepository: ProjectStorage = new ProjectStorage()
  val projectService: ProjectService = new ProjectService(database, projectRepository)
  val projectRouter: ProjectRoute = new ProjectRoute(projectService)

  "test" should {

    "test" in {
      Get("/project") ~> projectRouter.projectRoutes ~> check {
        status shouldBe StatusCodes.OK
      }
    }

  }

}