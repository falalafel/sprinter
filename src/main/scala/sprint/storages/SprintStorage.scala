package sprint.storages

import project.domain.ProjectId
import slick.dbio.DBIO
import slick.jdbc.PostgresProfile.api._
import sprint.domain.{Sprint, SprintId}

class SprintStorage {
  import Sprints._

  def getSprintsByProjectId(projectId: ProjectId): DBIO[Seq[Sprint]] =
    sprints.filter(_.projectId === projectId).result

  def getSprint(projectId: ProjectId, sprintId: SprintId): DBIO[Option[Sprint]] =
    sprints.filter(record => record.projectId === projectId && record.sprintId === sprintId).result.headOption

  def insertSprint(sprint: Sprint): DBIO[Int] = sprints += sprint

  def updateSprint(sprint: Sprint): DBIO[Int] =
    sprints.filter(record => record.projectId === sprint.projectId && record.sprintId === sprint.sprintId).update(sprint)

  def deleteSprint(projectId: ProjectId, sprintId: SprintId): DBIO[Int] =
    sprints.filter(record => record.projectId === projectId && record.sprintId === sprintId).delete

  private val sprints = TableQuery[Sprints]
}
