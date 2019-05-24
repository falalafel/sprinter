package user.storages

import slick.jdbc.PostgresProfile.api._
import user.domain.{Password, User, UserId}

class UserStorage {
  import Users._

  def getAllUsers: DBIO[Seq[User]] = users.result

  def getUserById(userId: UserId): DBIO[Option[User]] =
    users.filter(_.id === userId).result.headOption

  def getUserByIdAndPass(userId: UserId, password: Password): DBIO[Option[User]] =
    users.filter(users => users.id === userId && users.password === password).result.headOption

  def insertUser(user: User): DBIO[Int] = users += user

  def updateUser(user: User): DBIO[Int] =
    users.filter(_.id === user.userId).update(user)

  def deleteUser(userId: UserId): DBIO[Int] =
    users.filter(_.id === userId).delete

  private val users = TableQuery[Users]

}
