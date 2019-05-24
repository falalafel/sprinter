package main

import scala.util.Properties
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.headers._
import akka.http.scaladsl.server.Directives._
import akka.stream.{ActorMaterializer, Materializer}
import com.softwaremill.macwire._
import com.typesafe.config.ConfigFactory
import declaration.routes.DeclarationRoutes
import declaration.services.DeclarationService
import declaration.storages.DeclarationStorage
import project.routes.ProjectRoute
import project.services.ProjectService
import project.storages.ProjectStorage
import projectmembership.routes.ProjectMembershipRoutes
import projectmembership.services.ProjectMembershipService
import projectmembership.storages.ProjectMembershipStorage
import session.domain.SessionId
import session.routes.SessionRoutes
import session.services.SessionService
import session.storages.SessionStorage
import slick.jdbc.PostgresProfile.api._
import sprint.routes.SprintRoutes
import sprint.services.SprintService
import sprint.storages.SprintStorage
import user.routes.UserRoutes
import user.services.UserService
import user.storages.UserStorage
import week.routes.WeekRoute
import week.services.WeekService
import week.storages.WeekStorage
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import scala.concurrent.ExecutionContext
import ch.megard.akka.http.cors.scaladsl.CorsDirectives._

trait MainContext {

  implicit val system: ActorSystem

  implicit def executor: ExecutionContext

  implicit val materializer: Materializer

  lazy val config = ConfigFactory.load()
  lazy val configDbPath = "postgres"
  lazy val database = Database.forConfig(configDbPath, config)

  lazy val projectStorage: ProjectStorage = wire[ProjectStorage]
  lazy val projectService: ProjectService = wire[ProjectService]
  lazy val projectRoutes: ProjectRoute = wire[ProjectRoute]
  lazy val userStorage: UserStorage = wire[UserStorage]
  lazy val userService: UserService = wire[UserService]
  lazy val userRoutes: UserRoutes = wire[UserRoutes]
  lazy val sprintStorage: SprintStorage = wire[SprintStorage]
  lazy val sprintService: SprintService = wire[SprintService]
  lazy val sprintRoutes: SprintRoutes = wire[SprintRoutes]
  lazy val sessionStorage: SessionStorage = wire[SessionStorage]
  lazy val sessionService: SessionService = wire[SessionService]
  lazy val sessionRoutes: SessionRoutes = wire[SessionRoutes]
  lazy val weekStorage: WeekStorage = wire[WeekStorage]
  lazy val weekService: WeekService = wire[WeekService]
  lazy val weekRoutes: WeekRoute = wire[WeekRoute]
  lazy val declarationStorage: DeclarationStorage = wire[DeclarationStorage]
  lazy val declarationService: DeclarationService = wire[DeclarationService]
  lazy val declarationRoutes: DeclarationRoutes = wire[DeclarationRoutes]
  lazy val projectMembershipStorage: ProjectMembershipStorage = wire[ProjectMembershipStorage]
  lazy val projectMembershipService: ProjectMembershipService = wire[ProjectMembershipService]
  lazy val projectMembershipRoutes: ProjectMembershipRoutes = wire[ProjectMembershipRoutes]

  lazy val routes = cors() {
    path("signin") {
      authenticateBasicAsync(realm = "secure", sessionService.logIn) { session =>
        setCookie(HttpCookie("sprinter-client", session.sessionId.sessionId.toString)) {
          complete(session.userId.id)
        }
      }
    } ~ cookie("sprinter-client") { hash =>
      onSuccess(sessionService.authorize(SessionId(hash.value.toInt))) {
        case Some(userId) =>
          projectRoutes.projectRoutes ~ userRoutes.userRoutes
        case None =>
          complete("Not logged in")
      }
    }
  }
}

object Main extends App with MainContext {

  implicit val system: ActorSystem = ActorSystem()
  implicit val executor: ExecutionContext = system.dispatcher
  implicit val materializer: Materializer = ActorMaterializer()

  val interface = config.getString("http.interface")
  val port = Properties.envOrNone("PORT") match {
    case Some(portEnv) => portEnv.toInt
    case None => config.getInt("http.port")
  }
  Http().bindAndHandle(routes, interface, port)
}
