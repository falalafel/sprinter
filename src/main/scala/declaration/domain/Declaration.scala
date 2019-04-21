package declaration.domain

import project.domain.ProjectId
import sprint.domain.SprintId
import user.domain.UserId

case class HoursAvailable(value: Int) extends AnyVal

case class WorkNeeded(value: Int) extends AnyVal

case class DeclarationComment(comment: String) extends AnyVal

case class Declaration(projectId: ProjectId,
                       sprintId: SprintId,
                       userId: UserId,
                       hoursAvailable: HoursAvailable,
                       workNeeded: WorkNeeded,
                       comment: DeclarationComment)

case class DeclarationCreate(hoursAvailable: HoursAvailable,
                             workNeeded: WorkNeeded,
                             comment: DeclarationComment) {
  def toDeclaration(projectId: ProjectId,
                    sprintId: SprintId,
                    userId: UserId) =
    Declaration(projectId: ProjectId,
      sprintId: SprintId,
      userId: UserId,
      hoursAvailable: HoursAvailable,
      workNeeded: WorkNeeded,
      comment: DeclarationComment)
}
