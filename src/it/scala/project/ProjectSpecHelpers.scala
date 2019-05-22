package project

import java.time.LocalDate
import project.domain.{ProjectClosingStatus, ProjectCreate, ProjectName, ProjectStartDate, ProjectUpdate, SprintDuration, ProjectStartingFactor}

object ProjectSpecHelpers {

  def testProjectCreate = ProjectCreate(ProjectName("xd"), ProjectStartDate(LocalDate.now()), SprintDuration(7), ProjectStartingFactor(2.5))

  def testProjectUpdate(projectName: ProjectName, projectClosingStatus: ProjectClosingStatus) =
    ProjectUpdate(Some(projectName), Some(projectClosingStatus))

}
