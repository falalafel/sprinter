package week

import user.domain.UserId
import week.domain.{Hours, WeekDay, WeekPut}

object WeekSpecHelpers {

  def testWeekPut(userId: UserId) = WeekPut(WeekDay.Mon, Hours(8))

}
