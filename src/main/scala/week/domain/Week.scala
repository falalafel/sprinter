package week.domain

import user.domain.UserId
import week.domain.WeekDay.WeekDay

case object WeekDay extends Enumeration {
  type WeekDay = Value
  val Mon, Tue, Wed, Thu, Fri = Value
}

case class Hours(amount: Int) extends AnyVal

case class Week(userId: UserId, weekDay: WeekDay, hours: Hours)

case class WeekPut(weekDay: WeekDay, hours: Hours) {
  def toWeek(userId: UserId) =
    Week(userId, weekDay, hours)
}