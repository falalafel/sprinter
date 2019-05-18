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
    SprintDuration(20),
    ProjectStartingFactor(2.5)
  )

  def sprintCreate = SprintCreate(SprintStartDate(LocalDate.now()), SprintEndDate(LocalDate.of(2077, 12, 30)))

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
