package session.services

import session.domain.{Session, SessionId}
import session.storages.SessionStorage
import user.domain.UserId
import slick.jdbc.PostgresProfile.api._
import scala.concurrent.{ExecutionContext, Future}

class SessionService(db: Database, sessionStorage: SessionStorage)
                    (implicit ec: ExecutionContext){

  def getSessions: Future[Seq[Session]] =
    db.run(sessionStorage.getAllSessions)

  def getSession(userId: UserId): Future[Option[Session]] =
    db.run(sessionStorage.getSessionByUserId(userId))

  def createSession(session: Session): Future[SessionId] =
    db.run(sessionStorage.insertSession(session).map(_ => session.sessionId))


}