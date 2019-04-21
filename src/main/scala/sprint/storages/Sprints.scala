package sprint.storages

import project.domain.ProjectId
import slick.jdbc.PostgresProfile.api._
import slick.lifted.ProvenShape
import sprint.domain.{Sprint, SprintClosingStatus, SprintId, SprintStartDate}
import project.storages.Projects

object Sprints {
  implicit val sprintIdImpl: BaseColumnType[SprintId] = MappedColumnType.base(_.id, SprintId.apply)
  implicit val sprintStartDateImpl: BaseColumnType[SprintStartDate] = MappedColumnType.base(_.date, SprintStartDate)
  implicit val sprintClosingStatusImpl: BaseColumnType[SprintClosingStatus] = MappedColumnType.base(_.status, SprintClosingStatus)
}

class Sprints(tag: Tag) extends Table[Sprint](tag, "sprint"){
  import Sprints._
  import Projects._

  def sprintId: Rep[SprintId] = column[SprintId]("sprintid")

  def projectId: Rep[ProjectId] = column[ProjectId]("projectid")

  def startDate: Rep[SprintStartDate] = column[SprintStartDate]("startdate")

  def closingStatus: Rep[SprintClosingStatus] = column[SprintClosingStatus]("closed")

  def * : ProvenShape[Sprint] = (projectId, sprintId, startDate, closingStatus) <> (Sprint.tupled, Sprint.unapply)
}
