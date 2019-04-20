import java.sql.Timestamp
import io.circe.Decoder.Result
import io.circe.{Decoder, Encoder, HCursor, Json}
import io.circe.generic.extras.semiauto._
import io.circe.generic.extras.defaults._
import session.domain._

package object session {

  implicit val TimestampFormat : Encoder[Timestamp] with Decoder[Timestamp] = new Encoder[Timestamp] with Decoder[Timestamp] {
    override def apply(a: Timestamp): Json = Encoder.encodeLong.apply(a.getTime)
    override def apply(c: HCursor): Result[Timestamp] = Decoder.decodeLong.map(s => new Timestamp(s)).apply(c)
  }

  implicit val sessionDecoder: Decoder[Session] = deriveDecoder
  implicit val sessionEncoder: Encoder[Session] = deriveEncoder
  implicit val sessionIdDecoder: Decoder[SessionId] = deriveUnwrappedDecoder
  implicit val sessionIdEncoder: Encoder[SessionId] = deriveUnwrappedEncoder
  implicit val sessionCreateDecoder: Decoder[SessionCreate] = deriveDecoder
  implicit val sessionCreateEncoder: Encoder[SessionCreate] = deriveEncoder
  implicit val sessionCookieHashDecoder: Decoder[SessionCookieHash] = deriveUnwrappedDecoder
  implicit val sessionCookieHashEncoder: Encoder[SessionCookieHash] = deriveUnwrappedEncoder
  implicit val sessionTimeStampDecoder: Decoder[SessionTimeStamp] = deriveUnwrappedDecoder
  implicit val sessionTimeStampEncoder: Encoder[SessionTimeStamp] = deriveUnwrappedEncoder
}

