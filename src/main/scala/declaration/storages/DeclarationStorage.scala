package declaration.storages

import declaration.domain.Declaration
import project.domain.ProjectId
import slick.dbio.DBIO
import slick.jdbc.PostgresProfile.api._
import sprint.domain.SprintId
import user.domain.UserId

class DeclarationStorage {
  import Declarations._

  def getDeclarationsForSprint(projectId: ProjectId, sprintId: SprintId): DBIO[Seq[Declaration]] =
    declarations.filter(record => record.projectId === projectId && record.sprintId === sprintId).result

  def getDeclaration(projectId: ProjectId, sprintId: SprintId, userId: UserId): DBIO[Option[Declaration]] =
    declarations.filter(record =>
      record.projectId === projectId &&
      record.sprintId === sprintId &&
      record.userId === userId
    ).result.headOption

  def insertDeclaration(declaration: Declaration): DBIO[Int] = declarations += declaration

  def deleteDeclaration(projectId: ProjectId, sprintId: SprintId, userId: UserId): DBIO[Int] =
    declarations.filter(record =>
      record.projectId === projectId &&
      record.sprintId === sprintId &&
      record.userId === userId
    ).delete

  private val declarations = TableQuery[Declarations]
}
