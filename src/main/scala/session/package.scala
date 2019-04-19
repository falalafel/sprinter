import io.circe.{Decoder, Encoder}
import io.circe.generic.extras.semiauto._
import io.circe.generic.extras.defaults._
import session.domain._
import user.domain.UserId

package object session {
  implicit val sessionDecoder: Decoder[Session] = deriveDecoder
  implicit val sessionEncoder: Encoder[Session] = deriveEncoder
  implicit val sessionIdDecoder: Decoder[SessionId] = deriveUnwrappedDecoder
  implicit val sessionIdEncoder: Encoder[SessionId] = deriveUnwrappedEncoder

  implicit val userIdDecoder: Decoder[UserId] = deriveUnwrappedDecoder
  implicit val userIdEncoder: Encoder[UserId] = deriveUnwrappedEncoder
  implicit val sessionCreateDecoder: Decoder[SessionCreate] = deriveDecoder
  implicit val sessionCreateEncoder: Encoder[SessionCreate] = deriveEncoder
  implicit val sessionCookieHashDecoder: Decoder[SessionCookieHash] = deriveUnwrappedDecoder
  implicit val sessionCookieHashEncoder: Encoder[SessionCookieHash] = deriveUnwrappedEncoder
  implicit val sessionTimeStampDecoder: Decoder[SessionTimeStamp] = deriveUnwrappedDecoder
  implicit val sessionTimeStampEncoder: Encoder[SessionTimeStamp] = deriveUnwrappedEncoder
}

