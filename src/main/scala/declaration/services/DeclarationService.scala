package declaration.services

import slick.jdbc.PostgresProfile.api._
import com.rms.miu.slickcats.DBIOInstances._
import cats.data.OptionT
import declaration.domain.Declaration
import declaration.storages.DeclarationStorage
import project.domain.ProjectId
import sprint.domain.SprintId
import user.domain.UserId

import scala.concurrent.{ExecutionContext, Future}

class DeclarationService(db: Database, declarationStorage: DeclarationStorage)
  (implicit ec: ExecutionContext) {

  def getDeclarationsForSprint(projectId: ProjectId, sprintId: SprintId): Future[Seq[Declaration]] =
    db.run(declarationStorage.getDeclarationsForSprint(projectId, sprintId))

  def getDeclaration(projectId: ProjectId, sprintId: SprintId, userId: UserId): Future[Option[Declaration]] =
    db.run(declarationStorage.getDeclaration(projectId, sprintId, userId))

  def createDeclaration(declaration: Declaration): Future[(ProjectId, SprintId, UserId)] =
    db.run(declarationStorage.insertDeclaration(declaration).map(_ => (declaration.projectId, declaration.sprintId, declaration.userId)))

  def deleteDeclaration(projectId: ProjectId, sprintId: SprintId, userId: UserId): Future[Option[Int]] = {
    val query = for {
      _ <- OptionT(declarationStorage.getDeclaration(projectId, sprintId, userId))
      result <- OptionT.liftF(declarationStorage.deleteDeclaration(projectId, sprintId, userId))
    } yield result

    db.run(query.value)
  }
}