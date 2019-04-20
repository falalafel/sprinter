import io.circe.{Decoder, Encoder}
import week.domain.{Hours, Week, WeekDay, WeekPut}
import io.circe.generic.extras.semiauto._
import io.circe.generic.extras.defaults._
import week.domain.WeekDay.WeekDay

package object week {
  implicit val weekDecoder: Decoder[Week] = deriveDecoder
  implicit val weekEncoder: Encoder[Week] = deriveEncoder
  implicit val weekPutDecoder: Decoder[WeekPut] = deriveDecoder
  implicit val weekPutEncoder: Encoder[WeekPut] = deriveEncoder
  implicit val weekDayDecoder: Decoder[WeekDay] = Decoder.enumDecoder(WeekDay)
  implicit val weekDayEncoder: Encoder[WeekDay] = Encoder.enumEncoder(WeekDay)
  implicit val hoursDecoder: Decoder[Hours] = deriveUnwrappedDecoder
  implicit val hoursEncoder: Encoder[Hours] = deriveUnwrappedEncoder
}
