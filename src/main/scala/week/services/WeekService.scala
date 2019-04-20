package week.services

import slick.jdbc.PostgresProfile.api._
import user.domain.UserId
import week.domain.Week
import week.storages.WeekStorage

import scala.concurrent.{ExecutionContext, Future}

class WeekService(db: Database, weekStorage: WeekStorage)
                 (implicit ec: ExecutionContext) {

  def getWeekDaysByUser(userId: UserId): Future[Seq[Week]] =
    db.run(weekStorage.getWeekDaysByUser(userId))

  def insertOrUpdate(week: Week): Future[Int] =
    db.run(weekStorage.insertOrUpdate(week))

}
