package session.domain

import scala.util.Random
import java.sql.Timestamp
import user.domain.UserId

object SessionId {
  def generate: SessionId = SessionId(Random.nextInt().abs)
}

object SessionTimeStamp {
  def generate: SessionTimeStamp = SessionTimeStamp(new Timestamp(System.currentTimeMillis()))
}

object SessionCookieHash {
  def generate: SessionCookieHash = SessionCookieHash(Random.nextString(10))
}

case class SessionId(sessionId: Int) extends AnyVal
case class SessionCookieHash(cookieHash: String) extends AnyVal
case class SessionTimeStamp(timeStamp: Timestamp) extends AnyVal

case class Session(sessionId: SessionId = SessionId.generate,
                   userId: UserId,
                   cookieHash: SessionCookieHash = SessionCookieHash.generate,
                   timeStamp: SessionTimeStamp = SessionTimeStamp.generate)

case class SessionCreate(cookieHash: SessionCookieHash) {
  def toSession(userId: UserId) =
    Session(SessionId.generate,
            userId,
            cookieHash,
            SessionTimeStamp.generate)
}