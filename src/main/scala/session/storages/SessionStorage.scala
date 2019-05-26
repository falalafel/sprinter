package session.storages

import session.domain.{Session, SessionId}
import user.domain.UserId
import slick.jdbc.PostgresProfile.api._

class SessionStorage {
  import Sessions._

  def getAllSessions: DBIO[Seq[Session]] = sessions.result

  def getSessionByUserId(userId: UserId): DBIO[Seq[Session]] =
    sessions.filter(_.userId === userId).result

  def getSessionById(sessionId: SessionId): DBIO[Option[Session]] =
    sessions.filter(_.sessionId === sessionId).result.headOption

  def insertSession(session: Session): DBIO[Session] = (sessions returning sessions) += session

  private val sessions = TableQuery[Sessions]
}
