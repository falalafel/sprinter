package user.routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import akka.stream.Materializer
import user.services.UserService
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import user.domain.{UserCreate, UserId, UserUpdate}
import io.circe.syntax._
import scala.concurrent.ExecutionContext

class UserRoutes(userService: UserService)
                  (implicit mat: Materializer, ec: ExecutionContext) {

  def userRoutes: Route = pathPrefix("user") {

    (get & pathEndOrSingleSlash) {
      complete(userService.getUsers)
    } ~
      post {
        entity(as[UserCreate]) { userCreate =>
          complete(StatusCodes.Created, userService.createUser(userCreate.toUser))
        }
      } ~
      pathPrefix(IntNumber.map(UserId.apply)) { id =>
        pathEndOrSingleSlash {
          get {
            complete(userService.getUser(id).map{
              case Some(user) => StatusCodes.NoContent -> user.asJson
              case None => StatusCodes.NotFound -> id.asJson
            })
          } ~
            patch {
              entity(as[UserUpdate]) { projectUpdate =>
                complete(userService.updateUser(id, projectUpdate))
              }
            } ~
              delete {
                complete(userService.deleteUser(id))
              }
        }
      }
  }
}
