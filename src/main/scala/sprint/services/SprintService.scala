package sprint.services

import slick.jdbc.PostgresProfile.api._
import com.rms.miu.slickcats.DBIOInstances._
import cats.data.OptionT
import project.domain.ProjectId
import sprint.domain.{Sprint, SprintId, SprintUpdate}
import sprint.storages.SprintStorage

import scala.concurrent.{ExecutionContext, Future}

class SprintService(db: Database, sprintStorage: SprintStorage)
  (implicit ec: ExecutionContext) {

  def getSprintsFromProject(projectId: ProjectId): Future[Seq[Sprint]] =
    db.run(sprintStorage.getSprintsByProjectId(projectId))

  def getSprint(projectId: ProjectId, sprintId: SprintId): Future[Option[Sprint]] =
    db.run(sprintStorage.getSprint(projectId, sprintId))

  def createSprint(sprint: Sprint): Future[(ProjectId, SprintId)] =
    db.run(sprintStorage.insertSprint(sprint).map(_ => (sprint.projectId, sprint.sprintId)))

  def updateSprint(projectId: ProjectId, sprintId: SprintId, sprintUpdate: SprintUpdate): Future[Option[Int]] = {
    val query = for {
      sprint <- OptionT(sprintStorage.getSprint(projectId, sprintId))
      updatedSprint = sprintUpdate.update(sprint)
      result <- OptionT.liftF(sprintStorage.updateSprint(updatedSprint))
    } yield result

    db.run(query.value)
  }

  def deleteSprint(projectId: ProjectId, sprintId: SprintId): Future[Int] =
    db.run(sprintStorage.deleteSprint(projectId, sprintId))
}