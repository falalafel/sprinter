package projectmembership.routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import akka.stream.Materializer
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import io.circe.syntax._
import projectmembership.domain.ProjectMembershipCreate
import projectmembership.services.ProjectMembershipService
import project.domain.ProjectId
import user.domain.UserId


import scala.concurrent.ExecutionContext

class ProjectMembershipRoutes(projectMembershipService: ProjectMembershipService)
                             (implicit mat: Materializer, ec: ExecutionContext) {

  def projectMembershipRoutes(projectId: ProjectId): Route = {
    (get & pathEndOrSingleSlash) {
      complete(projectMembershipService.getProjectMembershipsForProject(projectId))
    } ~ pathPrefix(IntNumber.map(UserId.apply)) { userId =>
      get {
        complete(projectMembershipService.getProjectMembership(projectId, userId).map {
          case Some(projectMembership) => StatusCodes.OK -> projectMembership.asJson
          case None => StatusCodes.NotFound -> (projectId, userId).asJson
        })
      } ~ put {
        entity(as[ProjectMembershipCreate]) { projectMembershipCreate =>
          complete(StatusCodes.NoContent, projectMembershipService.insertOrUpdateProjectMembership(
            projectMembershipCreate.toProjectMembership(projectId, userId)))
        }
      } ~ delete {
        complete(projectMembershipService.deleteProjectMembership(projectId, userId).map {
          case Some(projectMembership) => StatusCodes.NoContent -> projectMembership.asJson
          case None => StatusCodes.NotFound -> (projectId, userId).asJson
        })
      }
    }
  }
}
