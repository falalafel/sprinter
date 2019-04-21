package declaration

import java.time.LocalDate

import declaration.domain.{DeclarationComment, DeclarationCreate, HoursAvailable, WorkNeeded}
import project.domain._
import sprint.domain._
import user.domain._

object DeclarationSpecHelpers {

  def projectCreate = ProjectCreate(
    ProjectName("[declaration test] project"),
    ProjectStartDate(LocalDate.now()),
    SprintDuration(20)
  )

  def sprintCreate = SprintCreate(SprintStartDate(LocalDate.now()))

  def userCreate = UserCreate(
    FullName("[declaration test] John Snow"),
    Mail("user@company.com"),
    Password("Dr5Uv234Qw"),
    Role(0)
  )

  def declarationCreate = DeclarationCreate(
    HoursAvailable(18),
    WorkNeeded(6),
    DeclarationComment("wazzup")
  )
}
