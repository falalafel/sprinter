package week.storages

import slick.jdbc.PostgresProfile.api._
import slick.lifted.ProvenShape
import user.domain.UserId
import user.storages.Users
import week.domain.{Hours, Week, WeekDay}
import week.domain.WeekDay.WeekDay

object Weeks {
  implicit val weekDayImpl: BaseColumnType[WeekDay] = MappedColumnType.base[WeekDay, String](
    e => e.toString,
    s => WeekDay.withName(s)
  )
  implicit val hoursImpl: BaseColumnType[Hours] = MappedColumnType.base(_.amount, Hours.apply)
}

class Weeks(tag: Tag) extends Table[Week](tag, "week") {
  import Weeks._
  import Users._

  def userId: Rep[UserId] = column[UserId]("userid")

  def weekDay: Rep[WeekDay] = column[WeekDay]("day")

  def hours: Rep[Hours] = column[Hours]("hours")

  def pk = primaryKey("week_pk", (userId, weekDay))

  def * : ProvenShape[Week] = (userId, weekDay, hours) <> (Week.tupled, Week.unapply)
}
