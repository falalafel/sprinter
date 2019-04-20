package sprint.domain

import java.time.LocalDate
import project.domain.ProjectId
import scala.util.Random

object SprintId {
  // TODO: generate sprintId based on current project sprints count
  def generate: SprintId = SprintId(Random.nextInt().abs)
}

case class SprintId(id: Int) extends AnyVal
case class SprintStartDate(date: LocalDate) extends AnyVal
case class SprintClosingStatus(status: Boolean) extends AnyVal

case class Sprint(
  projectId: ProjectId,
  sprintId: SprintId,
  startDate: SprintStartDate,
  closingStatus: SprintClosingStatus
)

case class SprintUpdate(
  closingStatus: Option[SprintClosingStatus]
) {
  def toSprint(sprint: Sprint): Sprint =
    Sprint(
      sprint.projectId,
      sprint.sprintId,
      sprint.startDate,
      closingStatus.getOrElse(sprint.closingStatus)
    )
}

case class SprintCreate(
  startDate: SprintStartDate
) {
  def toSprint(projectId: ProjectId) = Sprint(
    projectId,
    SprintId.generate,
    startDate,
    SprintClosingStatus(false)
  )
}
