package sprint.domain

import java.time.LocalDate
import project.domain.ProjectId

case class SprintId(id: Int) extends AnyVal {
  def inc() = SprintId(id + 1)
}

case class SprintStartDate(date: LocalDate) extends AnyVal

case class SprintEndDate(date: LocalDate) extends AnyVal

case class SprintClosingStatus(status: Boolean) extends AnyVal

case class SprintFactor(value: Double) extends AnyVal

case class SprintOriginalEstimatedHours(value: Int) extends AnyVal

case class SprintEndPlannedHours(value: Int) extends AnyVal

case class SprintBurnedHours(value: Int) extends AnyVal

case class SprintEffectiveFactor(value: Double) extends AnyVal

case class SprintEffectiveFactorWithHistory(value: Double) extends AnyVal

case class Sprint(
                   projectId: ProjectId,
                   sprintId: SprintId,
                   startDate: SprintStartDate,
                   endDate: SprintEndDate,
                   closingStatus: SprintClosingStatus,
                   factor: SprintFactor,
                   originalEstimatedHours: Option[SprintOriginalEstimatedHours] = None,
                   endPlannedHours: Option[SprintEndPlannedHours] = None,
                   burnedHours: Option[SprintBurnedHours] = None,
                   effectiveFactor: Option[SprintEffectiveFactor] = None,
                   effectiveFactorWithHistory: Option[SprintEffectiveFactorWithHistory] = None
                 )

case class SprintUpdate(closingStatus: SprintClosingStatus,
                        originalEstimatedHours: SprintOriginalEstimatedHours,
                        endPlannedHours: SprintEndPlannedHours,
                        burnedHours: SprintBurnedHours) {
  def toSprint(sprint: Sprint, effectiveFactor: SprintEffectiveFactor,
               effectiveFactorWithHistory: SprintEffectiveFactorWithHistory): Option[Sprint] =
    sprint.closingStatus match {
      case SprintClosingStatus(true) =>
        None

      case SprintClosingStatus(false) =>
        closingStatus match {
          case SprintClosingStatus(true) =>
            Some(Sprint(
              sprint.projectId,
              sprint.sprintId,
              sprint.startDate,
              sprint.endDate,
              closingStatus,
              sprint.factor,
              Some(originalEstimatedHours),
              Some(endPlannedHours),
              Some(burnedHours),
              Some(effectiveFactor),
              Some(effectiveFactorWithHistory)
            ))

          case SprintClosingStatus(false) =>
            None
        }
    }
}

case class SprintCreate(startDate: SprintStartDate,
                        endDate: SprintEndDate) {
  def toSprint(projectId: ProjectId, sprintId: SprintId, factor: SprintFactor) = Sprint(
    projectId,
    sprintId,
    startDate,
    endDate,
    SprintClosingStatus(false),
    factor
  )
}
