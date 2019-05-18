package sprint.domain

import java.time.LocalDate
import project.domain.ProjectId
import scala.util.Random

object SprintId {
  // TODO: generate sprintId based on current project sprints count
  def generate: SprintId = SprintId(Random.nextInt().abs)
}

object SprintFactor {
  // TODO: implement calculateFactorForSprint
  def calculateFactorForSprint: SprintFactor = SprintFactor(2.5)
}

case class SprintId(id: Int) extends AnyVal
case class SprintStartDate(date: LocalDate) extends AnyVal
case class SprintEndDate(date: LocalDate) extends AnyVal
case class SprintClosingStatus(status: Boolean) extends AnyVal
case class SprintFactor(factor: Double) extends AnyVal
case class SprintOriginalEstimatedHours(originalEstimatedHours: Int) extends AnyVal
case class SprintEndPlannedHours(endPlannedHours: Int) extends AnyVal
case class SprintBurnedHours(burnedHours: Int) extends AnyVal

case class Sprint(
  projectId: ProjectId,
  sprintId: SprintId,
  startDate: SprintStartDate,
  endDate: SprintEndDate,
  closingStatus: SprintClosingStatus,
  factor: SprintFactor,
  originalEstimatedHours: Option[SprintOriginalEstimatedHours] = None,
  endPlannedHours: Option[SprintEndPlannedHours] = None,
  burnedHours: Option[SprintBurnedHours] = None
)

case class SprintUpdate(closingStatus: Option[SprintClosingStatus],
                        originalEstimatedHours: Option[SprintOriginalEstimatedHours],
                        endPlannedHours: Option[SprintEndPlannedHours],
                        burnedHours: Option[SprintBurnedHours]) {
  def toSprint(sprint: Sprint): Sprint =
    Sprint(
      sprint.projectId,
      sprint.sprintId,
      sprint.startDate,
      sprint.endDate,
      closingStatus.getOrElse(sprint.closingStatus),
      sprint.factor,
      originalEstimatedHours,
      endPlannedHours,
      burnedHours
    )
}

case class SprintCreate(startDate: SprintStartDate,
                        endDate: SprintEndDate) {
  def toSprint(projectId: ProjectId) = Sprint(
    projectId,
    SprintId.generate,
    startDate,
    endDate,
    SprintClosingStatus(false),
    SprintFactor.calculateFactorForSprint
  )
}
