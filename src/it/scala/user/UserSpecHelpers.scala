package user

import user.domain.{FullName, Mail, Password, Role, UserCreate, UserUpdate}

object UserSpecHelpers {

  def testUserCreate = UserCreate(FullName("John Snow"), Mail("user@company.com"), Password("Dr5Uv234Qw"), Role(0))

  def testUserUpdate(name: FullName = FullName("xd"),
                     mail: Mail = Mail("xd"),
                     password: Password = Password("xd"),
                     role: Role = Role(0)) =
    UserUpdate(Some(name), Some(mail), Some(password), Some(role))

}
