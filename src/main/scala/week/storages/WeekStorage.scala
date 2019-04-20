package week.storages

import slick.jdbc.PostgresProfile.api._
import user.domain.UserId
import week.domain.Week

class WeekStorage {
  import user.storages.Users._

  def getWeekDaysByUser(userId: UserId): DBIO[Seq[Week]] =
    weeks.filter(_.userId === userId).result

  def insertOrUpdate(week: Week): DBIO[Int] = weeks.insertOrUpdate(week)

  private val weeks = TableQuery[Weeks]

}
