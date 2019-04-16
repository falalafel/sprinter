package project.routes

import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import akka.stream.Materializer
import project.services.ProjectService
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import io.circe.generic.auto._
import project.domain.{Project, ProjectCreate, ProjectId, ProjectUpdate}

import scala.concurrent.ExecutionContext

class ProjectRoute(projectService: ProjectService)
                  (implicit mat: Materializer, ec: ExecutionContext) {

  def projectRoutes: Route = pathPrefix("project") {

    (get & pathEndOrSingleSlash) {
      complete(projectService.getProjects)
    } ~
    post {
      entity(as[ProjectCreate]) { projectCreate =>
        complete(projectService.createProject(projectCreate.toProject))
      }
    } ~
    pathPrefix(IntNumber.map(ProjectId.apply)) { id =>
      pathEndOrSingleSlash {
        get {
          complete(projectService.getProject(id))
        } ~
        patch {
          entity(as[ProjectUpdate]) { projectUpdate =>
            complete(projectService.updateProject(id, projectUpdate))
          }
        } ~
        delete {
          complete(projectService.deleteProject(id))
        }
      }
    }
  }
}
