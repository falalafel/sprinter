package session


import session.domain._
import user.domain.UserId

object SessionSpecHelpers {

  def testSessionCreate(userId : UserId) = SessionCreate(userId, SessionCookieHash("asd"))
}
