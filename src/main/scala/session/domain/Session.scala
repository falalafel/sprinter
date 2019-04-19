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

case class SessionId(sessionId: Int) extends AnyVal
case class SessionCookieHash(cookieHash: String) extends AnyVal
case class SessionTimeStamp(timeStamp: Timestamp) extends AnyVal

case class Session(sessionId: SessionId,
                   userId: UserId,
                   cookieHash: SessionCookieHash,
                   timeStamp: SessionTimeStamp)


case class SessionCreate(userId: UserId,
                         cookieHash: SessionCookieHash) {
  def toSession =
    Session(SessionId.generate,
            userId,
            cookieHash,
            SessionTimeStamp.generate)
}