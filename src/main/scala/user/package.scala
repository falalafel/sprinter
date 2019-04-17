import io.circe.{Decoder, Encoder}
import io.circe.generic.extras.semiauto._
import io.circe.generic.extras.defaults._
import user.domain._


package object user {

  implicit val userDecoder: Decoder[User] = deriveDecoder
  implicit val userEncoder: Encoder[User] = deriveEncoder
  implicit val userIdDecoder: Decoder[UserId] = deriveUnwrappedDecoder
  implicit val userIdEncoder: Encoder[UserId] = deriveUnwrappedEncoder

  implicit val userCreateDecoder: Decoder[UserCreate] = deriveDecoder
  implicit val userCreateEncoder: Encoder[UserCreate] = deriveEncoder
  implicit val userRoleDecoder: Decoder[Role] = deriveUnwrappedDecoder
  implicit val userRoleEncoder: Encoder[Role] = deriveUnwrappedEncoder
  implicit val userPasswordDecoder: Decoder[Password] = deriveUnwrappedDecoder
  implicit val userPasswordEncoder: Encoder[Password] = deriveUnwrappedEncoder

  implicit val userUpdateDecoder: Decoder[UserUpdate] = deriveDecoder
  implicit val userUpdateEncoder: Encoder[UserUpdate] = deriveEncoder
  implicit val userFullNameDecoder: Decoder[FullName] = deriveUnwrappedDecoder
  implicit val userFullNameEncoder: Encoder[FullName] = deriveUnwrappedEncoder
  implicit val userMailDecoder: Decoder[Mail] = deriveUnwrappedDecoder
  implicit val userMailEncoder: Encoder[Mail] = deriveUnwrappedEncoder
}
