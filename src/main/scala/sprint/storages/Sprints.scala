package sprint.storages

import project.domain.ProjectId
import slick.jdbc.PostgresProfile.api._
import slick.lifted.ProvenShape
import sprint.domain.{
  Sprint, SprintClosingStatus, SprintId, SprintStartDate, SprintEndDate, SprintFactor,
  SprintBurnedHours, SprintEndPlannedHours, SprintOriginalEstimatedHours
}
import project.storages.Projects

object Sprints {
  implicit val sprintIdImpl: BaseColumnType[SprintId] = MappedColumnType.base(_.id, SprintId.apply)
  implicit val sprintStartDateImpl: BaseColumnType[SprintStartDate] = MappedColumnType.base(_.date, SprintStartDate)
  implicit val sprintEndDateImpl: BaseColumnType[SprintEndDate] = MappedColumnType.base(_.date, SprintEndDate)
  implicit val sprintClosingStatusImpl: BaseColumnType[SprintClosingStatus] = MappedColumnType.base(_.status, SprintClosingStatus)
  implicit val sprintFactorImpl: BaseColumnType[SprintFactor] = MappedColumnType.base(_.factor, SprintFactor.apply)
  implicit val sprintOriginalEstimatedHoursImpl: BaseColumnType[SprintOriginalEstimatedHours] = MappedColumnType.base(_.originalEstimatedHours, SprintOriginalEstimatedHours)
  implicit val sprintEndPlannedHoursImpl: BaseColumnType[SprintEndPlannedHours] = MappedColumnType.base(_.endPlannedHours, SprintEndPlannedHours)
  implicit val sprintBurnedHoursImpl: BaseColumnType[SprintBurnedHours] = MappedColumnType.base(_.burnedHours, SprintBurnedHours)
}

class Sprints(tag: Tag) extends Table[Sprint](tag, "sprint") {

  import Sprints._
  import Projects._

  def sprintId: Rep[SprintId] = column[SprintId]("sprintid")

  def projectId: Rep[ProjectId] = column[ProjectId]("projectid")

  def startDate: Rep[SprintStartDate] = column[SprintStartDate]("startdate")

  def endDate: Rep[SprintEndDate] = column[SprintEndDate]("enddate")

  def closingStatus: Rep[SprintClosingStatus] = column[SprintClosingStatus]("closed")

  def factor: Rep[SprintFactor] = column[SprintFactor]("factor")

  def originalEstimatedHours: Rep[Option[SprintOriginalEstimatedHours]] = column[Option[SprintOriginalEstimatedHours]]("original_estimated_hours")

  def endPlannedHours: Rep[Option[SprintEndPlannedHours]] = column[Option[SprintEndPlannedHours]]("end_planned_hours")

  def burnedHours: Rep[Option[SprintBurnedHours]] = column[Option[SprintBurnedHours]]("burned_hours")

  def * : ProvenShape[Sprint] = (projectId, sprintId, startDate, endDate, closingStatus,
    factor, originalEstimatedHours, endPlannedHours, burnedHours) <> (Sprint.tupled, Sprint.unapply)
}
