import io.circe.{Decoder, Encoder}
import io.circe.generic.extras.semiauto._
import io.circe.generic.extras.defaults._
import declaration.domain._

package object declaration {

  implicit val declarationDecoder: Decoder[Declaration] = deriveDecoder
  implicit val declarationEncoder: Encoder[Declaration] = deriveEncoder

  implicit val declarationCreateDecoder: Decoder[DeclarationCreate] = deriveDecoder
  implicit val declarationCreateEncoder: Encoder[DeclarationCreate] = deriveEncoder

  implicit val hoursAvailableDecoder: Decoder[HoursAvailable] = deriveUnwrappedDecoder
  implicit val hoursAvailableEncoder: Encoder[HoursAvailable] = deriveUnwrappedEncoder

  implicit val workNeededDecoder: Decoder[WorkNeeded] = deriveUnwrappedDecoder
  implicit val workNeededEncoder: Encoder[WorkNeeded] = deriveUnwrappedEncoder

  implicit val declarationCommentDecoder: Decoder[DeclarationComment] = deriveUnwrappedDecoder
  implicit val declarationCommentEncoder: Encoder[DeclarationComment] = deriveUnwrappedEncoder
}
