package projectmembership.storages

import slick.jdbc.PostgresProfile.api._
import slick.dbio.DBIO
import projectmembership.domain.ProjectMembership
import project.domain.ProjectId
import user.domain.UserId

class ProjectMembershipStorage {
  import user.storages.Users._
  import project.storages.Projects._

  def getProjectMembershipsForProject(projectId: ProjectId): DBIO[Seq[ProjectMembership]] =
    projectMemberships.filter(_.projectId === projectId).result

  def getProjectMembershipsForUser(userId: UserId): DBIO[Seq[ProjectMembership]] =
    projectMemberships.filter(_.userId === userId).result

  def getProjectMembership(projectId: ProjectId, userId: UserId): DBIO[Option[ProjectMembership]] =
    projectMemberships.filter(record => record.projectId === projectId && record.userId === userId).result.headOption

  def insertOrUpdateProjectMembership(projectMembership: ProjectMembership): DBIO[Int] =
    projectMemberships.insertOrUpdate(projectMembership)

  def deleteProjectMembership(projectId: ProjectId, userId: UserId): DBIO[Int] =
    projectMemberships.filter(record => record.projectId === projectId && record.userId === userId).delete

  private val projectMemberships = TableQuery[ProjectMemberships]
}
