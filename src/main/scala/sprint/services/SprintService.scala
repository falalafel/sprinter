package sprint.services

import slick.jdbc.PostgresProfile.api._
import com.rms.miu.slickcats.DBIOInstances._
import cats.data.OptionT
import declaration.storages.DeclarationStorage
import project.domain.ProjectId
import project.storages.ProjectStorage
import sprint.domain.{Sprint, SprintCreate, SprintEffectiveFactor, SprintEffectiveFactorWithHistory, SprintId, SprintUpdate}
import sprint.storages.SprintStorage
import scala.concurrent.{ExecutionContext, Future}

class SprintService(db: Database, sprintStorage: SprintStorage,
                    projectStorage: ProjectStorage,
                    declarationStorage: DeclarationStorage)
                   (implicit ec: ExecutionContext) {

  def getSprintsFromProject(projectId: ProjectId): Future[Seq[Sprint]] =
    db.run(sprintStorage.getSprintsByProjectId(projectId))

  def getSprint(projectId: ProjectId, sprintId: SprintId): Future[Option[Sprint]] =
    db.run(sprintStorage.getSprint(projectId, sprintId))

  def createSprint(sprint: SprintCreate, projectId: ProjectId): Future[Option[(ProjectId, SprintId)]] = {
    val lastSprintOpt: DBIO[Option[Sprint]] = sprintStorage.getSprintsByProjectId(projectId).flatMap {
      case Nil =>
        projectStorage.getProjectById(projectId).map {
          _.map { project =>
            sprint.toSprint(projectId, SprintId(0), project.startingFactor)
              .copy(effectiveFactorWithHistory = Some(project.startingFactor))
          }
        }
      case sprints => DBIO.successful(Some(sprints.maxBy(_.sprintId.id)))
    }.transactionally

    val query = for {
      lastSprint <- OptionT(lastSprintOpt)
      factorWithHistory <- OptionT.fromOption(lastSprint.effectiveFactorWithHistory)
      _ <- OptionT.liftF(sprintStorage
        .insertSprint(sprint.toSprint(projectId, lastSprint.sprintId.inc(), factorWithHistory)))
    } yield (projectId, lastSprint.sprintId.inc())

    db.run(query.value)
  }

  def updateSprint(projectId: ProjectId, sprintId: SprintId, sprintUpdate: SprintUpdate): Future[Option[Int]] = {

    val factors: DBIO[(SprintEffectiveFactor, SprintEffectiveFactorWithHistory)] = for {
      declarations <- declarationStorage.getDeclarationsForSprint(projectId, sprintId)
      sprints <- sprintStorage.getSprintsByProjectId(projectId)
    } yield {

      val sumWorked = declarations.foldLeft(0)((acc, decl) => acc + decl.hoursAvailable.value - decl.workNeeded.value)
      val effectiveHoursNeeded = (sprintUpdate.endPlannedHours.value - sprintUpdate.burnedHours.value) *
        (sumWorked.toDouble / sprintUpdate.burnedHours.value.toDouble) + sumWorked
      val effectiveFactor = effectiveHoursNeeded / sprintUpdate.endPlannedHours.value

      val pastEffectiveFactors = sprints.flatMap {
        _.effectiveFactor match {
          case None => Nil
          case Some(value) => Seq(value)
        }
      }

      val fullEffectiveFactors = pastEffectiveFactors :+ SprintEffectiveFactor(effectiveFactor)
      val effectiveFactorWithHistory = fullEffectiveFactors.foldLeft(0.0)((acc, factor) => acc + factor.value) / fullEffectiveFactors.length

      (SprintEffectiveFactor(effectiveFactor), SprintEffectiveFactorWithHistory(effectiveFactorWithHistory))
    }

    val query = for {
      sprint <- OptionT(sprintStorage.getSprint(projectId, sprintId))
      (effective, effectiveWithHistory) <- OptionT.liftF(factors)
      updatedSprint <- OptionT.fromOption(sprintUpdate.toSprint(sprint, effective, effectiveWithHistory))
      result <- OptionT.liftF(sprintStorage.updateSprint(updatedSprint))
    } yield result

    db.run(query.value)
  }

  def deleteSprint(projectId: ProjectId, sprintId: SprintId): Future[Option[Int]] = {
    val query = for {
      _ <- OptionT(sprintStorage.getSprint(projectId, sprintId))
      result <- OptionT.liftF(sprintStorage.deleteSprint(projectId, sprintId))
    } yield result

    db.run(query.value)
  }
}
