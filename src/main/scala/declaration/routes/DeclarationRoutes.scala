package declaration.routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import akka.stream.Materializer
import project.domain.ProjectId
import sprint.domain.SprintId
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import declaration.domain.DeclarationCreate
import declaration.services.DeclarationService
import io.circe.syntax._
import user.domain.UserId

import scala.concurrent.ExecutionContext

class DeclarationRoutes(declarationService: DeclarationService)
  (implicit mat: Materializer, ec: ExecutionContext) {

  def declarationRoutes(projectId: ProjectId, sprintId: SprintId): Route = {
    (get & pathEndOrSingleSlash) {
      complete(declarationService.getDeclarationsForSprint(projectId, sprintId))
    } ~ pathPrefix(IntNumber.map(UserId.apply)) { userId =>
      get {
        complete(declarationService.getDeclaration(projectId, sprintId, userId).map {
          case Some(declaration) => StatusCodes.OK -> declaration.asJson
          case None => StatusCodes.NotFound -> (projectId, sprintId, userId).asJson
        })
      } ~ post {
        entity(as[DeclarationCreate]) { declarationCreate =>
          complete(StatusCodes.Created, declarationService.createDeclaration(
            declarationCreate.toDeclaration(projectId, sprintId, userId)))
        }
      } ~ delete {
        complete(declarationService.deleteDeclaration(projectId, sprintId, userId).map {
          case Some(declaration) => StatusCodes.NoContent -> declaration.asJson
          case None => StatusCodes.NotFound -> (projectId, sprintId, userId).asJson
        })
      }
    }
  }
}
