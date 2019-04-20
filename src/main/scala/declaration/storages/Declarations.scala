package declaration.storages

import declaration.domain.{Declaration, DeclarationComment, HoursAvailable, WorkNeeded}
import project.domain.ProjectId
import slick.jdbc.PostgresProfile.api._
import slick.lifted.ProvenShape
import sprint.domain.SprintId
import user.domain.UserId

object Declarations {
  implicit val projectIdImpl: BaseColumnType[ProjectId] = MappedColumnType.base(_.id, ProjectId.apply)
  implicit val sprintIdImpl: BaseColumnType[SprintId] = MappedColumnType.base(_.id, SprintId.apply)
  implicit val userIdImpl: BaseColumnType[UserId] = MappedColumnType.base(_.id, UserId.apply)
  implicit val hoursAvailableImpl: BaseColumnType[HoursAvailable] = MappedColumnType.base(_.value, HoursAvailable)
  implicit val WorkNeededImpl: BaseColumnType[WorkNeeded] = MappedColumnType.base(_.value, WorkNeeded)
  implicit val DeclarationCommentImpl: BaseColumnType[DeclarationComment] = MappedColumnType.base(_.comment, DeclarationComment)
}

class Declarations(tag: Tag) extends Table[Declaration](tag, "declaration"){
  import Declarations._

  def projectId: Rep[ProjectId] = column[ProjectId]("projectid")

  def sprintId: Rep[SprintId] = column[SprintId]("sprintid")

  def userId: Rep[UserId] = column[UserId]("userid")

  def hoursAvailable: Rep[HoursAvailable] = column[HoursAvailable]("hoursavailable")

  def workNeeded: Rep[WorkNeeded] = column[WorkNeeded]("workneeded")

  def comment: Rep[DeclarationComment] = column[DeclarationComment]("comment")

  def * : ProvenShape[Declaration] = (projectId, sprintId, userId, hoursAvailable, workNeeded, comment) <> (Declaration.tupled, Declaration.unapply)
}
