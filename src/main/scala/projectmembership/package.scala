import io.circe.{Decoder, Encoder}
import io.circe.generic.extras.semiauto._
import io.circe.generic.extras.defaults._
import projectmembership.domain._

package object projectmembership {

  implicit val projectMembershipDecoder: Decoder[ProjectMembership] = deriveDecoder
  implicit val projectMembershipEncoder: Encoder[ProjectMembership] = deriveEncoder

  implicit val projectMembershipCreateDecoder: Decoder[ProjectMembershipCreate] = deriveDecoder
  implicit val projectMembershipCreateEncoder: Encoder[ProjectMembershipCreate] = deriveEncoder

  implicit val isScrumMasterDecoder: Decoder[IsScrumMaster] = deriveUnwrappedDecoder
  implicit val isScrumMasterEncoder: Encoder[IsScrumMaster] = deriveUnwrappedEncoder

}
