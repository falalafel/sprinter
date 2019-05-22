package sprint

import java.time.LocalDate

import project.domain._
import sprint.domain._

object SprintSpecHelpers {

  def projectCreate = ProjectCreate(
    ProjectName("[sprint test] project"),
    ProjectStartDate(LocalDate.now()),
    SprintDuration(20),
    ProjectStartingFactor(2.5)
  )

  def sprintCreate = SprintCreate(SprintStartDate(LocalDate.now()),
    SprintEndDate(LocalDate.of(2077, 12, 30)))

  def sprintUpdate = SprintUpdate(
    SprintClosingStatus(true),
    SprintOriginalEstimatedHours(52),
    SprintEndPlannedHours(64),
    SprintBurnedHours(57)
  )

}
