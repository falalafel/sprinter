import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.{ActorMaterializer, Materializer}
import com.typesafe.config.ConfigFactory
import project.repositories.ProjectRepository
import project.routers.ProjectRouter
import project.services.ProjectService
import slick.jdbc.PostgresProfile.api._
import scala.concurrent.ExecutionContext
import com.softwaremill.macwire._

object Main extends App {

  implicit val system: ActorSystem = ActorSystem()
  implicit val executor: ExecutionContext = system.dispatcher
  implicit val materializer: Materializer = ActorMaterializer()

  lazy val config = ConfigFactory.load()
  lazy val configDbPath = "postgres"
  lazy val database = Database.forConfig(configDbPath, config)

  lazy val projectRepository: ProjectRepository = wire[ProjectRepository]
  lazy val projectService: ProjectService       = wire[ProjectService]
  lazy val projectRouter: ProjectRouter         = wire[ProjectRouter]

  val routes = projectRouter.projectRouters
  val interface = "localhost"
  val port = 8080
  Http().bindAndHandle(routes, interface, port)

}
