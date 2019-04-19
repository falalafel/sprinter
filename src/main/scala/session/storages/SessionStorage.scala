package session.storages

import project.domain.Project
import session.domain.Session
import user.domain.UserId
import slick.jdbc.PostgresProfile.api._

class SessionStorage {
  import Sessions._

  def getAllSessions: DBIO[Seq[Session]] = sessions.result

  def getSessionByUserId(userId: UserId): DBIO[Option[Session]] =
    sessions.filter(_.userId === userId).result.headOption

  def insertSession(session: Session): DBIO[Int] = sessions += session

  private val sessions = TableQuery[Sessions]
}
