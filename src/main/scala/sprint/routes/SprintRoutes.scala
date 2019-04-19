package sprint.routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import akka.stream.Materializer
import io.circe.syntax._
import project.domain.ProjectId
import sprint.domain.{SprintCreate, SprintId, SprintUpdate}
import sprint.services.SprintService
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._

import scala.concurrent.ExecutionContext
import sprint.sprintMarshalling._


class SprintRoutes(sprintService: SprintService)
  (implicit mat: Materializer, ec: ExecutionContext) {

  def sprintRoutes(projectId: ProjectId): Route = pathPrefix("sprint") {

    (get & pathEndOrSingleSlash) {
      complete(sprintService.getSprintsFromProject(projectId))
    } ~
      post {
        entity(as[SprintCreate]) { sprintCreate =>
          complete(StatusCodes.Created, sprintService.createSprint(sprintCreate.toSprint))
        }
      } ~
      pathPrefix(IntNumber.map(SprintId.apply)) { id =>
        pathEndOrSingleSlash {
          get {
            complete(sprintService.getSprint(id).map {
              case Some(xd) => StatusCodes.NoContent -> xd.asJson
              case None => StatusCodes.NotFound -> id.asJson
            })
          } ~
            patch {
              entity(as[SprintUpdate]) { sprintUpdate =>
                complete(sprintService.updateSprint(id, sprintUpdate))
              }
            } ~
            delete {
              complete(sprintService.deleteSprint(id))
            }
        }
      }
  }
}
