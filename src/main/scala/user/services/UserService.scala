package user.services

import user.domain.{User, UserId, UserUpdate}
import user.storages.UserStorage
import slick.jdbc.PostgresProfile.api._
import com.rms.miu.slickcats.DBIOInstances._
import cats.data.OptionT
import scala.concurrent.{ExecutionContext, Future}

class UserService(db: Database, userStorage: UserStorage)
                    (implicit ec: ExecutionContext){

  def getUsers: Future[Seq[User]] =
    db.run(userStorage.getAllUsers)

  def getUser(userId: UserId): Future[Option[User]] =
    db.run(userStorage.getUserById(userId))

  def createUser(user: User): Future[UserId] =
    db.run(userStorage.insertUser(user).map(_ => user.userId))

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
