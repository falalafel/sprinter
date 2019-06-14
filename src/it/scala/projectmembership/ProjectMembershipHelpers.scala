package projectmembership

import java.time.LocalDate

import projectmembership.domain.{ProjectMembershipCreate, IsScrumMaster}
import project.domain._
import user.domain._

object ProjectMembershipHelpers {

  def projectCreate = ProjectCreate(
    ProjectName("[projectmembership test] project"),
    ProjectStartDate(LocalDate.now()),
    SprintDuration(20),
    ProjectStartingFactor(2.5)
  )

  def userCreate = UserCreate(
    FullName("[projectmembership test] user"),
    Mail.generate,
    Role(0)
  )

  def projectMembershipCreate = ProjectMembershipCreate(
    IsScrumMaster(false)
  )
}
