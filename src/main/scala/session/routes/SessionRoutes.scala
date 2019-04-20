package session.routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import akka.stream.Materializer
import session.services.SessionService
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import session.domain.SessionCreate
import user.domain.UserId
import scala.concurrent.ExecutionContext

class SessionRoutes(sessionService: SessionService)
                   (implicit mat: Materializer, ec: ExecutionContext) {

  def sessionRoutes(userId: UserId): Route =
    (get & pathEndOrSingleSlash) {
      complete(sessionService.getSessionsByUser(userId))
    } ~
      post {
        entity(as[SessionCreate]) { sessionCreate =>
          complete(StatusCodes.Created, sessionService.createSession(sessionCreate.toSession(userId)))
        }
      }
}
