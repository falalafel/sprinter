package user.services

import user.domain.{User, UserId, UserNoPassword, UserUpdate}
import user.storages.UserStorage
import slick.jdbc.PostgresProfile.api._
import com.rms.miu.slickcats.DBIOInstances._
import cats.data.OptionT
import notification.UserCreationService

import scala.concurrent.{ExecutionContext, Future}

class UserService(db: Database, userStorage: UserStorage, notificationService: UserCreationService)
                    (implicit ec: ExecutionContext){

  def getUsers: Future[Seq[UserNoPassword]] =
    db.run(userStorage.getAllUsers.map(_.map(_.withoutPassword)))

  def getUser(userId: UserId): Future[Option[UserNoPassword]] =
    db.run(userStorage.getUserById(userId).map(_.map(_.withoutPassword)))

  def createUser(user: User): Future[UserId] = {
    val result = db.run(userStorage.insertUser(user).map(_ => user.userId))

    notificationService.sendMail(user.mail.mail, user.password)

    result
  }

  def updateUser(userId: UserId, userUpdate: UserUpdate): Future[Option[Int]] = {
    val query = for {
      user <- OptionT(userStorage.getUserById(userId))
      oldPassword <- OptionT.fromOption(userUpdate.oldPassword) if user.password == oldPassword 
      updatedUser = userUpdate.update(user)
      result <- OptionT.liftF(userStorage.updateUser(updatedUser))
    } yield result

    db.run(query.value)
  }

  def deleteUser(userId: UserId): Future[Option[Int]] = {
    val query = for {
      _ <- OptionT(userStorage.getUserById(userId))
      result <- OptionT.liftF(userStorage.deleteUser(userId))
    } yield result

    db.run(query.value)
  }

}
