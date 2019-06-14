package user.domain

import scala.util.Random

object UserId {
  def generate: UserId = UserId(Random.nextInt().abs)
}

case class UserId(id: Int) extends AnyVal

case class FullName(name: String) extends AnyVal

case class Mail(mail: String) extends AnyVal

object Mail {
  def generate: Mail = Mail(s"${Random.alphanumeric.take(6).mkString}@company.com")
}

case class Password(password: String) extends AnyVal

object Password {
  def generate: Password = Password(Random.alphanumeric.take(8).mkString)
}

case class Role(role: Int) extends AnyVal

case class User(userId: UserId,
                name: FullName,
                mail: Mail,
                password: Password,
                role: Role) {
  def withoutPassword = UserNoPassword(userId, name, mail, role)
}

case class UserNoPassword(userId: UserId,
                          name: FullName,
                          mail: Mail,
                          role: Role)

case class UserUpdate(name: Option[FullName],
                      mail: Option[Mail],
                      oldPassword: Option[Password],
                      password: Option[Password],
                      role: Option[Role]) {
  def update(user: User): User =
    User(user.userId,
      name.getOrElse(user.name),
      mail.getOrElse(user.mail),
      password.getOrElse(user.password),
      role.getOrElse(user.role))
}

case class UserCreate(name: FullName,
                      mail: Mail,
                      role: Role) {
  def toUser =
    User(UserId.generate,
      name,
      mail,
      Password.generate,
      role)
}
