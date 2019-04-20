package sprint

import java.time.LocalDate

import project.domain._
import sprint.domain._

object SprintSpecHelpers {

  def projectCreate = ProjectCreate(
    ProjectName("[sprint test] project"),
    ProjectStartDate(LocalDate.now()),
    SprintDuration(20)
  )

  def sprintCreate = SprintCreate(SprintStartDate(LocalDate.now()))

  def sprintUpdate = SprintUpdate(Some(SprintClosingStatus(true)))

}
