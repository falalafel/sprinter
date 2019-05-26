package session.services

import akka.http.scaladsl.server.directives.Credentials
import cats.data.OptionT
import session.domain.{Session, SessionId}
import session.storages.SessionStorage
import user.domain.UserId
import slick.jdbc.PostgresProfile.api._
import user.storages.UserStorage
import com.rms.miu.slickcats.DBIOInstances._
import scala.concurrent.{ExecutionContext, Future}

class SessionService(db: Database, sessionStorage: SessionStorage, userStorage: UserStorage)
                    (implicit ec: ExecutionContext){

  def getSessions: Future[Seq[Session]] =
    db.run(sessionStorage.getAllSessions)

  def getSessionsByUser(userId: UserId): Future[Seq[Session]] =
    db.run(sessionStorage.getSessionByUserId(userId))

  def createSession(session: Session): Future[SessionId] =
    db.run(sessionStorage.insertSession(session).map(_ => session.sessionId))

  def logIn(cred: Credentials): Future[Option[Session]] = {
    val query = cred match {
      case provided @ Credentials.Provided(userId) =>
        val query = for {
          user <- OptionT(userStorage.getUserById(UserId(userId.toInt))) if provided.verify(user.password.password)
          cookieHash <- OptionT.liftF(sessionStorage.insertSession(Session(userId = user.userId)))
        } yield cookieHash
        query.value
      case _ =>
        DBIO.successful(None)
    }

    db.run(query)
  }

  def authorize(sessionId: SessionId): Future[Option[Int]] = {
    val query = for {
      session <- OptionT(sessionStorage.getSessionById(sessionId))
      user <- OptionT(userStorage.getUserById(session.userId))
    } yield user.userId.id

    db.run(query.value)
  }

}