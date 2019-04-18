package main

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.stream.{ActorMaterializer, Materializer}
import com.softwaremill.macwire._
import com.typesafe.config.ConfigFactory
import project.routes.ProjectRoute
import project.services.ProjectService
import project.storages.ProjectStorage
import slick.jdbc.PostgresProfile.api._
import user.routes.UserRoutes
import user.services.UserService
import user.storages.UserStorage
import scala.concurrent.ExecutionContext

trait MainContext {

  implicit val system: ActorSystem
  implicit def executor: ExecutionContext
  implicit val materializer: Materializer

  lazy val config = ConfigFactory.load()
  lazy val configDbPath = "postgres"
  lazy val database = Database.forConfig(configDbPath, config)

  lazy val projectStorage: ProjectStorage = wire[ProjectStorage]
  lazy val projectService: ProjectService = wire[ProjectService]
  lazy val projectRoutes: ProjectRoute    = wire[ProjectRoute]

  lazy val userStorage: UserStorage = wire[UserStorage]
  lazy val userService: UserService = wire[UserService]
  lazy val userRoutes: UserRoutes = wire[UserRoutes]

  lazy val routes = projectRoutes.projectRoutes ~ userRoutes.userRoutes
}

object Main extends App with MainContext {

  implicit val system: ActorSystem = ActorSystem()
  implicit val executor: ExecutionContext = system.dispatcher
  implicit val materializer: Materializer = ActorMaterializer()

  val interface = "localhost"
  val port = 8080
  Http().bindAndHandle(routes, interface, port)
}
