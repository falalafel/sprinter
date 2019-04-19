package sprint.routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import akka.stream.Materializer
import project.domain.ProjectId
import sprint.domain.{SprintCreate, SprintId, SprintUpdate}
import sprint.services.SprintService
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import io.circe.syntax._

import scala.concurrent.ExecutionContext
import sprint.sprintMarshalling._


class SprintRoutes(sprintService: SprintService)
  (implicit mat: Materializer, ec: ExecutionContext) {

  def sprintRoutes(projectId: ProjectId): Route = {


    (get & pathEndOrSingleSlash) {
      complete(sprintService.getSprintsFromProject(projectId))
    } ~
    post {
      entity(as[SprintCreate]) { sprintCreate =>
        complete(StatusCodes.Created, sprintService.createSprint(sprintCreate.toSprint))
      }
    } ~
    pathPrefix(IntNumber.map(SprintId.apply)) { sprintId =>
      get {
        complete(sprintService.getSprint(projectId, sprintId).map {
              case Some(sprint) => StatusCodes.OK -> sprint.asJson
              case None => StatusCodes.NotFound -> (projectId, sprintId).asJson
            })
      } ~
      patch {
        entity(as[SprintUpdate]) { sprintUpdate =>
          complete(sprintService.updateSprint(projectId, sprintId, sprintUpdate))
        }
      } ~
      delete {
        complete(sprintService.deleteSprint(projectId, sprintId))
      }
    }
  }
}
