package session.routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import akka.stream.Materializer
import session.services.SessionService
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import session.domain.SessionCreate
import user.domain.UserId
import io.circe.syntax._
import scala.concurrent.ExecutionContext

class SessionRoutes(sessionService: SessionService)
                    (implicit mat: Materializer, ec: ExecutionContext) {

  def sessionRoutes: Route = pathPrefix("session") {

    (get & pathEndOrSingleSlash) {
      complete(sessionService.getSessions)
    } ~
      post {
        entity(as[SessionCreate]) { sessionCreate =>
          complete(StatusCodes.Created, sessionService.createSession(sessionCreate.toSession))
        }
      } ~
      pathPrefix(IntNumber.map(UserId.apply)) { id =>
        pathEndOrSingleSlash {
          get {
            complete(sessionService.getSession(id).map{
              case Some(session) => StatusCodes.NoContent -> session.asJson
              case None => StatusCodes.NotFound -> id.asJson
            })
          }
        }
      }
  }

}
