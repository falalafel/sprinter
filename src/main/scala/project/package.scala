import java.time.LocalDate

import io.circe.{Decoder, Encoder}
import io.circe.generic.extras.semiauto._
import io.circe.generic.extras.defaults._
import project.domain._

package object project {

  implicit val fooDecoder: Decoder[Project] = deriveDecoder
  implicit val fooEncoder: Encoder[Project] = deriveEncoder
  implicit val fooDecoder223: Decoder[ProjectId] = deriveUnwrappedDecoder
  implicit val fooEncoder223: Encoder[ProjectId] = deriveUnwrappedEncoder

  implicit val fooDecoder2: Decoder[ProjectCreate] = deriveDecoder
  implicit val fooEncoder2: Encoder[ProjectCreate] = deriveEncoder
  implicit val fooDecoder22: Decoder[ProjectStartDate] = deriveUnwrappedDecoder
  implicit val fooEncoder22: Encoder[ProjectStartDate] = deriveUnwrappedEncoder
  implicit val fooDecoder23: Decoder[SprintDuration] = deriveUnwrappedDecoder
  implicit val fooEncoder23: Encoder[SprintDuration] = deriveUnwrappedEncoder

  implicit val fooDecoder3: Decoder[ProjectUpdate] = deriveDecoder
  implicit val fooEncoder3: Encoder[ProjectUpdate] = deriveEncoder
  implicit val fooDecoder4: Decoder[ProjectName] = deriveUnwrappedDecoder
  implicit val fooEncoder4: Encoder[ProjectName] = deriveUnwrappedEncoder
  implicit val fooDecoder5: Decoder[ProjectClosingStatus] = deriveUnwrappedDecoder
  implicit val fooEncoder5: Encoder[ProjectClosingStatus] = deriveUnwrappedEncoder

}
