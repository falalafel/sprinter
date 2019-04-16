package project.storages

import project.domain.{Project, ProjectClosingStatus, ProjectId, ProjectName, ProjectStartDate, SprintDuration}
import slick.jdbc.PostgresProfile.api._
import slick.lifted.ProvenShape

object Projects {
  implicit val projectIdImpl: BaseColumnType[ProjectId] = MappedColumnType.base(_.id, ProjectId.apply)
  implicit val projectNameImpl: BaseColumnType[ProjectName] = MappedColumnType.base(_.name, ProjectName)
  implicit val projectStartDateImpl: BaseColumnType[ProjectStartDate] = MappedColumnType.base(_.date, ProjectStartDate)
  implicit val sprintDurationImpl: BaseColumnType[SprintDuration] = MappedColumnType.base(_.days, SprintDuration)
  implicit val projectClosingStatusImpl: BaseColumnType[ProjectClosingStatus] = MappedColumnType.base(_.status, ProjectClosingStatus)
}

class Projects(tag: Tag) extends Table[Project](tag, "project") {
  import Projects._

  def id: Rep[ProjectId] = column[ProjectId]("projectid", O.PrimaryKey)

  def name: Rep[ProjectName] = column[ProjectName]("name")

  def startDate: Rep[ProjectStartDate] = column[ProjectStartDate]("startdate")

  def duration: Rep[SprintDuration] = column[SprintDuration]("duration")

  def closingStatus: Rep[ProjectClosingStatus] = column[ProjectClosingStatus]("closed")

  def * : ProvenShape[Project] = (id, name, startDate, duration, closingStatus) <> (Project.tupled, Project.unapply)
}
