package user.routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import akka.stream.Materializer
import user.services.UserService
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import user.domain.{UserCreate, UserId, UserUpdate}
import io.circe.syntax._
import projectmembership.services.ProjectMembershipService
import session.routes.SessionRoutes
import week.routes.WeekRoute

import scala.concurrent.ExecutionContext

class UserRoutes(userService: UserService,
                 sessionRoutes: SessionRoutes,
                 weekRoute: WeekRoute,
                 projectMembershipService: ProjectMembershipService)
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
        pathPrefix("memberships") {
          complete(projectMembershipService.getUsersMemberships(id))
        } ~ pathEndOrSingleSlash {
          get {
            complete(userService.getUser(id).map {
              case Some(user) => StatusCodes.OK -> user.asJson
              case None => StatusCodes.NotFound -> id.asJson
            })
          } ~
            patch {
              entity(as[UserUpdate]) { userUpdate =>
                complete(userService.updateUser(id, userUpdate).map {
                  case Some(i) => StatusCodes.NoContent -> i.asJson
                  case None => StatusCodes.NotFound -> id.asJson
                })
              }
            } ~
            delete {
              complete(userService.deleteUser(id).map {
                case Some(i) => StatusCodes.NoContent -> i.asJson
                case None => StatusCodes.NotFound -> id.asJson
              })
            }
        } ~ pathPrefix("session") {
          sessionRoutes.sessionRoutes(id)
        } ~ pathPrefix("week") {
          weekRoute.weekRoutes(id)
        }
      }
  }
}
