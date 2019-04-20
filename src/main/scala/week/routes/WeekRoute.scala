package week.routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.Materializer
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import user.domain.UserId
import week.domain.WeekPut
import week.services.WeekService
import scala.concurrent.ExecutionContext

class WeekRoute(weekService: WeekService)
               (implicit mat: Materializer, ec: ExecutionContext) {

  def weekRoutes(userId: UserId): Route =
    (get & pathEndOrSingleSlash) {
      complete(weekService.getWeekDaysByUser(userId))
    } ~
      put {
        entity(as[WeekPut]) { weekPut =>
          complete(StatusCodes.NoContent, weekService.insertOrUpdate(weekPut.toWeek(userId)))
        }
      }
}
