package session.storages

import session.domain.Session
import user.domain.UserId
import slick.jdbc.PostgresProfile.api._

class SessionStorage {
  import Sessions._

  def getAllSessions: DBIO[Seq[Session]] = sessions.result

  def getSessionByUserId(userId: UserId): DBIO[Seq[Session]] =
    sessions.filter(_.userId === userId).result

  def insertSession(session: Session): DBIO[Int] = sessions += session

  private val sessions = TableQuery[Sessions]
}
