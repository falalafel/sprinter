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

  def getSprint(sprintId: SprintId): Future[Option[Sprint]] =
    db.run(sprintStorage.getSprintById(sprintId))

  def createSprint(sprint: Sprint): Future[SprintId] =
    db.run(sprintStorage.insertSprint(sprint).map(_ => sprint.sprintId))

  def updateSprint(sprintId: SprintId, sprintUpdate: SprintUpdate): Future[Option[Int]] = {
    val query = for {
      sprint <- OptionT(sprintStorage.getSprintById(sprintId))
      updatedSprint = sprintUpdate.update(sprint)
      result <- OptionT.liftF(sprintStorage.updateSprint(updatedSprint))
    } yield result

    db.run(query.value)
  }

  def deleteSprint(sprintId: SprintId): Future[Int] =
    db.run(sprintStorage.deleteSprint(sprintId))
}