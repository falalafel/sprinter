package utils

import akka.http.scaladsl.testkit.ScalatestRouteTest
import declaration.DeclarationSpecHelpers.userCreate
import main.MainContext
import org.scalatest.{BeforeAndAfter, Matchers, WordSpec}
import session.domain.Session
import user.domain.{FullName, Mail, Password, Role, UserCreate}
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

trait TestHelpers {

  def result[T](futureResult: Future[T]): T =
    Await.result(futureResult, 15.seconds)

}

trait TemplateSpec extends WordSpec with Matchers with ScalatestRouteTest with MainContext with BeforeAndAfter with TestHelpers {

  //TODO move to separate stage to be executed once
  //TODO wrap all paths in Cookie with sessionId
  private def testUserCreate = UserCreate(FullName("Stub"), Mail("user@company.com"), Role(0))
  private val userCreateQuery = userService.createUser(userCreate.toUser)
  private val userId = result(userCreateQuery)
  private val sess = Session(userId = userId)
  private val usr = result(database.run(sessionStorage.insertSession(sess)))
  val sessionId: String = sess.sessionId.sessionId.toString

  override def afterAll {
    database.close()
  }

}
