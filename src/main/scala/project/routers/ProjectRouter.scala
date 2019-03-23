package project.routers

import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import akka.stream.Materializer
import project.services.ProjectService
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import io.circe.generic.auto._
import scala.concurrent.ExecutionContext

class ProjectRouter(projectService: ProjectService)
                   (implicit mat: Materializer, ec: ExecutionContext) {

  def projectRouters: Route = {
    (get & pathEndOrSingleSlash) {
      complete(projectService.getProjects)
    }
  }

}
