package user.domain

import scala.util.Random

object UserId {
  def generate: UserId = UserId(Random.nextInt().abs)
}

case class UserId(id: Int) extends AnyVal
case class FullName(name: String) extends AnyVal
case class Mail(mail: String) extends AnyVal
case class Password(password: String) extends AnyVal
case class Role(role: Int) extends AnyVal

case class User(userId: UserId,
                name: FullName,
                mail: Mail,
                password: Password,
                role: Role)

case class UserUpdate(name: Option[FullName],
                      mail: Option[Mail],
                      password: Option[Password],
                      role: Option[Role]) {
  def update(user: User) : User =
    User(user.userId,
         name.getOrElse(user.name),
         mail.getOrElse(user.mail),
         password.getOrElse(user.password),
         role.getOrElse(user.role))
}

case class UserCreate(name: FullName,
                      mail: Mail,
                      password: Password,
                      role: Role) {
  def toUser =
    User(UserId.generate,
         name,
         mail,
         password,
         role)
}
