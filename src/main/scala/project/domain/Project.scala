package project.domain

import java.time.LocalDate
import scala.util.Random

object ProjectId {
  def generate: ProjectId = ProjectId(Random.nextInt().abs)
}

case class ProjectId(id: Int) extends AnyVal

case class ProjectName(name: String) extends AnyVal

case class ProjectStartDate(date: LocalDate) extends AnyVal

case class SprintDuration(days: Int) extends AnyVal

case class ProjectClosingStatus(status: Boolean) extends AnyVal

case class ProjectStartingFactor(factor: Double) extends AnyVal

case class Project(projectId: ProjectId,
                   name: ProjectName,
                   startDate: ProjectStartDate,
                   sprintDuration: SprintDuration,
                   closingStatus: ProjectClosingStatus,
                   startingFactor: ProjectStartingFactor)

case class ProjectUpdate(name: Option[ProjectName],
                         closingStatus: Option[ProjectClosingStatus]) {
  def update(project: Project): Project =
    Project(project.projectId,
      name.getOrElse(project.name),
      project.startDate,
      project.sprintDuration,
      closingStatus.getOrElse(project.closingStatus),
      project.startingFactor)
}

case class ProjectCreate(name: ProjectName,
                         startDate: ProjectStartDate,
                         sprintDuration: SprintDuration,
                         startingFactor: ProjectStartingFactor) {
  def toProject =
    Project(ProjectId.generate,
      name,
      startDate,
      sprintDuration,
      ProjectClosingStatus(false),
      startingFactor)
}
