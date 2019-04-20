package sprint

import java.time.LocalDate

import project.domain._
import sprint.domain._

object SprintSpecHelpers {

  def projectCreate = ProjectCreate(
    ProjectName("proj-sprint-test"),
    ProjectStartDate(LocalDate.now()),
    SprintDuration(20)
  )

  def sprintCreate(projectId: ProjectId) = SprintCreate(projectId, SprintStartDate(LocalDate.now()))

  def sprintUpdate = SprintUpdate(Some(SprintClosingStatus(true)))

}
