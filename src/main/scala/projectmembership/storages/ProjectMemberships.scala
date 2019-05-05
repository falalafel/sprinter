package projectmembership.storages

import slick.jdbc.PostgresProfile.api._
import slick.lifted.ProvenShape
import projectmembership.domain.{ProjectMembership, IsScrumMaster}
import project.domain.ProjectId
import project.storages.Projects
import user.domain.UserId
import user.storages.Users


object ProjectMemberships {
  implicit val isScrumMasterImpl: BaseColumnType[IsScrumMaster] = MappedColumnType.base(_.value, IsScrumMaster)
}

class ProjectMemberships(tag: Tag) extends Table[ProjectMembership](tag, "projectmembership"){
  import ProjectMemberships._
  import Users._
  import Projects._

  def projectId: Rep[ProjectId] = column[ProjectId]("projectid")

  def userId: Rep[UserId] = column[UserId]("userid")

  def isScrumMaster: Rep[IsScrumMaster] = column[IsScrumMaster]("isscrummaster")

  def pk = primaryKey("projectmembership_pk", (projectId, userId))

  def * : ProvenShape[ProjectMembership] = (projectId, userId, isScrumMaster) <> (ProjectMembership.tupled, ProjectMembership.unapply)
}
