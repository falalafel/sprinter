package session.storages

import session.domain.{Session, SessionId, SessionCookieHash, SessionTimeStamp}
import user.domain.UserId
import slick.jdbc.PostgresProfile.api._
import slick.lifted.ProvenShape

object Sessions {
  implicit val sessionIdImpl: BaseColumnType[SessionId] = MappedColumnType.base(_.sessionId, SessionId.apply)
  implicit val userIdImpl: BaseColumnType[UserId] = MappedColumnType.base(_.id, UserId.apply)
  implicit val sessionCookieHashImpl: BaseColumnType[SessionCookieHash] = MappedColumnType.base(_.cookieHash, SessionCookieHash)
  implicit val sessionTimeStampImpl: BaseColumnType[SessionTimeStamp] = MappedColumnType.base(_.timeStamp, SessionTimeStamp.apply)
}

class Sessions(tag: Tag) extends Table[Session](tag, "session") {
  import Sessions._

  def sessionId: Rep[SessionId] = column[SessionId]("sessionid", O.PrimaryKey)

  def userId: Rep[UserId] = column[UserId]("userid")

  def cookieHash: Rep[SessionCookieHash] = column[SessionCookieHash]("cookiehash")

  def timeStamp: Rep[SessionTimeStamp] = column[SessionTimeStamp]("timestamp")


  def * : ProvenShape[Session] = (sessionId, userId, cookieHash, timeStamp) <> (Session.tupled, Session.unapply)
}