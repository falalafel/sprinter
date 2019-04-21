package declaration.storages

import declaration.domain.Declaration
import project.domain.ProjectId
import project.storages.Projects
import slick.dbio.DBIO
import slick.jdbc.PostgresProfile.api._
import sprint.domain.SprintId
import sprint.storages.Sprints
import user.domain.UserId
import user.storages.Users

class DeclarationStorage {
  import Users._
  import Projects._
  import Sprints._

  def getDeclarationsForSprint(projectId: ProjectId, sprintId: SprintId): DBIO[Seq[Declaration]] =
    declarations.filter(record => record.projectId === projectId && record.sprintId === sprintId).result

  def getDeclaration(projectId: ProjectId, sprintId: SprintId, userId: UserId): DBIO[Option[Declaration]] =
    declarations.filter(_.repEquals(projectId, sprintId, userId)).result.headOption

  def insertOrUpdateDeclaration(declaration: Declaration): DBIO[Int] = declarations.insertOrUpdate(declaration)

  def deleteDeclaration(projectId: ProjectId, sprintId: SprintId, userId: UserId): DBIO[Int] =
    declarations.filter(_.repEquals(projectId, sprintId, userId)).delete

  private val declarations = TableQuery[Declarations]

  implicit class eq(declaration: Declarations) {
    def repEquals(projectId: ProjectId, sprintId: SprintId, userId: UserId): Rep[Boolean] =
      declaration.projectId === projectId &&
        declaration.sprintId === sprintId &&
        declaration.userId === userId
  }

}
