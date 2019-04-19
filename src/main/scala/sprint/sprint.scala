import java.time.LocalDate

import io.circe.{Decoder, Encoder}
import io.circe.generic.extras.semiauto._
import io.circe.generic.extras.defaults._
import sprint.domain._


package object sprint {

  implicit val sprintIdEncoder: Encoder[SprintId] = deriveUnwrappedEncoder
  implicit val sprintIdDecoder: Decoder[SprintId] = deriveUnwrappedDecoder

  implicit val sprintStartDateEncoder: Encoder[SprintStartDate] = deriveUnwrappedEncoder
  implicit val sprintStartDateIdDecoder: Decoder[SprintStartDate] = deriveUnwrappedDecoder

  implicit val sprintClosingStatusEncoder: Encoder[SprintClosingStatus] = deriveUnwrappedEncoder
  implicit val sprintClosingStatusDecoder: Decoder[SprintClosingStatus] = deriveUnwrappedDecoder

  implicit val sprintEncoder: Encoder[Sprint] = deriveEncoder
  implicit val sprintDecoder: Decoder[Sprint] = deriveDecoder

  implicit val sprintCreateEncoder: Encoder[SprintCreate] = deriveEncoder
  implicit val sprintCreateDecoder: Decoder[SprintCreate] = deriveDecoder

  implicit val sprintUpdateEncoder: Encoder[SprintUpdate] = deriveEncoder
  implicit val sprintUpdateDecoder: Decoder[SprintUpdate] = deriveDecoder

}
