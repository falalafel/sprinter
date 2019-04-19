package user.storages

import user.domain._
import slick.jdbc.PostgresProfile.api._
import slick.lifted.ProvenShape

object Users {
  implicit val userIdImpl: BaseColumnType[UserId] = MappedColumnType.base(_.id, UserId.apply)
  implicit val fullNameImpl: BaseColumnType[FullName] = MappedColumnType.base(_.name, FullName.apply)
  implicit val mailImpl: BaseColumnType[Mail] = MappedColumnType.base(_.mail, Mail.apply)
  implicit val passwordImpl: BaseColumnType[Password] = MappedColumnType.base(_.password, Password.apply)
  implicit val roleImpl: BaseColumnType[Role] = MappedColumnType.base(_.role, Role.apply)
}

class Users(tag: Tag) extends Table[User](tag, "user") {
  import Users._

  def id: Rep[UserId] = column[UserId]("userid", O.PrimaryKey)

  def fullName: Rep[FullName] = column[FullName]("fullname")

  def mail: Rep[Mail] = column[Mail]("mail")

  def password: Rep[Password] = column[Password]("password")

  def role: Rep[Role] = column[Role]("role")

  def * : ProvenShape[User] = (id, fullName, mail, password, role) <> (User.tupled, User.unapply)
}
