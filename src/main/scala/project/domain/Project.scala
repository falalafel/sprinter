package project.domain

import java.time.LocalDate
import scala.util.Random

object ProjectId {
  def generate: ProjectId = ProjectId(Random.nextInt())
}

case class ProjectId(id: Int) extends AnyVal
case class ProjectName(name: String) extends AnyVal
case class ProjectStartDate(date: LocalDate) extends AnyVal
case class SprintDuration(days: Int) extends AnyVal
case class ProjectClosingStatus(status: Boolean) extends AnyVal

case class Project(projectId: ProjectId,
                   name: ProjectName,
                   startDate: ProjectStartDate,
                   sprintDuration: SprintDuration,
                   closingStatus: ProjectClosingStatus)

case class ProjectUpdate(name: Option[ProjectName],
                         closingStatus: Option[ProjectClosingStatus]) {
  def update(project: Project): Project =
    Project(project.projectId,
      name.getOrElse(project.name),
      project.startDate,
      project.sprintDuration,
      closingStatus.getOrElse(project.closingStatus))
}

case class ProjectCreate(name: ProjectName,
                              startDate: ProjectStartDate,
                              sprintDuration: SprintDuration) {
  def toProject =
    Project(ProjectId.generate,
            name,
            startDate,
            sprintDuration,
            ProjectClosingStatus(false))
}
