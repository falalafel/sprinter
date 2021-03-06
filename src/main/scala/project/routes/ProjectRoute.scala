package project.routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import akka.stream.Materializer
import project.services.ProjectService
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import project.domain.{ProjectCreate, ProjectId, ProjectUpdate}
import io.circe.syntax._
import projectmembership.routes.ProjectMembershipRoutes
import sprint.routes.SprintRoutes
import scala.concurrent.ExecutionContext

class ProjectRoute(projectService: ProjectService, sprintRoutes: SprintRoutes,
                   projectMembershipRoutes: ProjectMembershipRoutes)
                  (implicit mat: Materializer, ec: ExecutionContext) {

  def projectRoutes: Route = pathPrefix("project") {

    (get & pathEndOrSingleSlash) {
      complete(projectService.getProjects)
    } ~
      post {
        entity(as[ProjectCreate]) { projectCreate =>
          complete(StatusCodes.Created, projectService.createProject(projectCreate.toProject))
        }
      } ~
      pathPrefix(IntNumber.map(ProjectId.apply)) { id =>
        pathEndOrSingleSlash {
          get {
            complete(projectService.getProject(id).map {
              case Some(project) => StatusCodes.OK -> project.asJson
              case None => StatusCodes.NotFound -> id.asJson
            })
          } ~
            patch {
              entity(as[ProjectUpdate]) { projectUpdate =>
                complete(projectService.updateProject(id, projectUpdate).map {
                  case Some(i) => StatusCodes.NoContent -> i.asJson
                  case None => StatusCodes.NotFound -> id.asJson
                })
              }
            } ~
            delete {
              complete(projectService.deleteProject(id).map {
                case Some(i) => StatusCodes.NoContent -> i.asJson
                case None => StatusCodes.NotFound -> id.asJson
              })
            }
        } ~ pathPrefix("sprint") {
          sprintRoutes.sprintRoutes(id)
        } ~ pathPrefix("membership") {
          projectMembershipRoutes.projectMembershipRoutes(id)
        }
      }
  }
}
