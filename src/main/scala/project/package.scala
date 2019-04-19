import io.circe.{Decoder, Encoder}
import io.circe.generic.extras.semiauto._
import io.circe.generic.extras.defaults._
import project.domain._

package object project {
  implicit val projectDecoder: Decoder[Project] = deriveDecoder
  implicit val projectEncoder: Encoder[Project] = deriveEncoder
  implicit val projectIdDecoder: Decoder[ProjectId] = deriveUnwrappedDecoder
  implicit val projectIdEncoder: Encoder[ProjectId] = deriveUnwrappedEncoder

  implicit val projectCreateDecoder: Decoder[ProjectCreate] = deriveDecoder
  implicit val projectCreateEncoder: Encoder[ProjectCreate] = deriveEncoder
  implicit val projectStartDateDecoder: Decoder[ProjectStartDate] = deriveUnwrappedDecoder
  implicit val projectStartDateEncoder: Encoder[ProjectStartDate] = deriveUnwrappedEncoder
  implicit val sprintDurationDecoder: Decoder[SprintDuration] = deriveUnwrappedDecoder
  implicit val sprintDurationEncoder: Encoder[SprintDuration] = deriveUnwrappedEncoder

  implicit val projectUpdateDecoder: Decoder[ProjectUpdate] = deriveDecoder
  implicit val projectUpdateEncoder: Encoder[ProjectUpdate] = deriveEncoder
  implicit val projectNameDecoder: Decoder[ProjectName] = deriveUnwrappedDecoder
  implicit val projectNameEncoder: Encoder[ProjectName] = deriveUnwrappedEncoder
  implicit val projectClosingStatusDecoder: Decoder[ProjectClosingStatus] = deriveUnwrappedDecoder
  implicit val projectClosingStatusEncoder: Encoder[ProjectClosingStatus] = deriveUnwrappedEncoder
}
