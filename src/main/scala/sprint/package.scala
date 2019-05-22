import io.circe.{Decoder, Encoder}
import io.circe.generic.extras.semiauto._
import io.circe.generic.extras.defaults._
import project.domain.ProjectStartingFactor
import sprint.domain._


package object sprint {

  implicit val sprintIdEncoder: Encoder[SprintId] = deriveUnwrappedEncoder
  implicit val sprintIdDecoder: Decoder[SprintId] = deriveUnwrappedDecoder
  implicit val sprintStartDateEncoder: Encoder[SprintStartDate] = deriveUnwrappedEncoder
  implicit val sprintStartDateDecoder: Decoder[SprintStartDate] = deriveUnwrappedDecoder
  implicit val sprintClosingStatusEncoder: Encoder[SprintClosingStatus] = deriveUnwrappedEncoder
  implicit val sprintClosingStatusDecoder: Decoder[SprintClosingStatus] = deriveUnwrappedDecoder
  implicit val sprintEndDateEncoder: Encoder[SprintEndDate] = deriveUnwrappedEncoder
  implicit val sprintEndDateDecoder: Decoder[SprintEndDate] = deriveUnwrappedDecoder
  implicit val sprintFactorEncoder: Encoder[SprintFactor] = deriveUnwrappedEncoder
  implicit val sprintFactorDecoder: Decoder[SprintFactor] = deriveUnwrappedDecoder
  implicit val sprintOriginalEstimatedHoursEncoder: Encoder[SprintOriginalEstimatedHours] = deriveUnwrappedEncoder
  implicit val sprintOriginalEstimatedHoursDecoder: Decoder[SprintOriginalEstimatedHours] = deriveUnwrappedDecoder
  implicit val sprintEndPlannedHoursEncoder: Encoder[SprintEndPlannedHours] = deriveUnwrappedEncoder
  implicit val sprintEndPlannedHoursDecoder: Decoder[SprintEndPlannedHours] = deriveUnwrappedDecoder
  implicit val sprintBurnedHoursEncoder: Encoder[SprintBurnedHours] = deriveUnwrappedEncoder
  implicit val sprintBurnedHoursDecoder: Decoder[SprintBurnedHours] = deriveUnwrappedDecoder
  implicit val sprintEffectiveFactorEncoder: Encoder[SprintEffectiveFactor] = deriveUnwrappedEncoder
  implicit val sprintEffectiveFactorDecoder: Decoder[SprintEffectiveFactor] = deriveUnwrappedDecoder
  implicit val sprintEffectiveFactorWithHistoryEncoder: Encoder[SprintEffectiveFactorWithHistory] = deriveUnwrappedEncoder
  implicit val sprintEffectiveFactorWithHistoryDecoder: Decoder[SprintEffectiveFactorWithHistory] = deriveUnwrappedDecoder

  implicit val sprintEncoder: Encoder[Sprint] = deriveEncoder
  implicit val sprintDecoder: Decoder[Sprint] = deriveDecoder

  implicit val sprintCreateEncoder: Encoder[SprintCreate] = deriveEncoder
  implicit val sprintCreateDecoder: Decoder[SprintCreate] = deriveDecoder

  implicit val sprintUpdateEncoder: Encoder[SprintUpdate] = deriveEncoder
  implicit val sprintUpdateDecoder: Decoder[SprintUpdate] = deriveDecoder

  implicit def projFactor2SprintFactor(projectStartingFactor: ProjectStartingFactor): SprintFactor =
    SprintFactor(projectStartingFactor.factor)

  implicit def projFactor2SprintEffFactor(projectStartingFactor: ProjectStartingFactor): SprintEffectiveFactor =
    SprintEffectiveFactor(projectStartingFactor.factor)

  implicit def projStartFactor2SprintEffFactor(projectStartingFactor: ProjectStartingFactor): SprintEffectiveFactorWithHistory =
    SprintEffectiveFactorWithHistory(projectStartingFactor.factor)

  implicit def projStartFactorHist2SprFactor(sprintEffectiveFactorWithHistory: SprintEffectiveFactorWithHistory): SprintFactor =
    SprintFactor(sprintEffectiveFactorWithHistory.value)

}
