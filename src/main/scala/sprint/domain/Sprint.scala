package sprint.domain

import java.time.LocalDate

import project.domain.ProjectId

import scala.util.Random

object SprintId {
  def generate: SprintId = SprintId(Random.nextInt().abs)
}

case class SprintId(id: Int) extends AnyVal
case class SprintStartDate(date: LocalDate) extends AnyVal
case class SprintClosingStatus(status: Boolean) extends AnyVal

case class Sprint(
  sprintId: SprintId,
  projectId: ProjectId,
  startDate: SprintStartDate,
  closingStatus: SprintClosingStatus
)

case class SprintUpdate(
  closingStatus: Option[SprintClosingStatus]
) {
  def update(sprint: Sprint): Sprint =
    Sprint(
      sprint.sprintId,
      sprint.projectId,
      sprint.startDate,
      closingStatus.getOrElse(sprint.closingStatus)
    )
}

case class SprintCreate(
  projectId: ProjectId,
  startDate: SprintStartDate
) {
  def toSprint = Sprint(
    SprintId.generate,
    projectId,
    startDate,
    SprintClosingStatus(false)
  )
}
