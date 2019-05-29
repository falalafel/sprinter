package sprint.storages

import project.domain.ProjectId
import project.storages.Projects
import user.storages.Users
import projectmembership.storages.ProjectMemberships
import declaration.storages.Declarations
import slick.dbio.DBIO
import slick.jdbc.PostgresProfile.api._
import sprint.domain.{Sprint, SprintClosingStatus, SprintId}
import user.domain.User

class SprintStorage {
  import Sprints._
  import Projects._
  import Users._

  def getSprintsByProjectId(projectId: ProjectId): DBIO[Seq[Sprint]] =
    sprints.filter(_.projectId === projectId).result

  def getOpenSprints: DBIO[Seq[Sprint]] =
    sprints.filter(_.closingStatus === SprintClosingStatus.apply(false)).result

  def getSprint(projectId: ProjectId, sprintId: SprintId): DBIO[Option[Sprint]] =
    sprints.filter(record => record.projectId === projectId && record.sprintId === sprintId).result.headOption

  def insertSprint(sprint: Sprint): DBIO[Int] = sprints += sprint

  def updateSprint(sprint: Sprint): DBIO[Int] =
    sprints.filter(record => record.projectId === sprint.projectId && record.sprintId === sprint.sprintId).update(sprint)

  def deleteSprint(projectId: ProjectId, sprintId: SprintId): DBIO[Int] =
    sprints.filter(record => record.projectId === projectId && record.sprintId === sprintId).delete

/*  This is a correct PostgreSQL query:
      select u.userid from declaration as d
      right outer join "user" as u on d.userid = u.userid
      join projectmembership as pm on pm.userid = u.userid
      where d.userid is null and d.sprintid = SPRINTID;
    TODO: rewrite in the following function (using slick)
    Maybe there is a way to make a raw query? (e.g. using slick.jdbc.H2Profile)
*/
  def getUsersWithNoDeclaration(sprintId: SprintId): DBIO[Seq[User]] = {
    val query = for {
      (((d, u), pm), s) <- (declarations joinRight users on (_.userId === _.id)
        join projectmemberships on (_._2.id === _.userId)
        join sprints on (_._2.projectId === _.projectId))
        .filter(_._2.sprintId === sprintId)
    } yield u
  query.filter(_.id == null).result
} // not sure if will work

  private val sprints = TableQuery[Sprints]
  private val declarations = TableQuery[Declarations]
  private val users = TableQuery[Users]
  private val projectmemberships = TableQuery[ProjectMemberships]
}
