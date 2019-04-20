package utils

import akka.http.scaladsl.testkit.ScalatestRouteTest
import main.MainContext
import org.scalatest.{BeforeAndAfter, Matchers, WordSpec}
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

trait TestHelpers {

  def result[T](futureResult: Future[T]): T =
    Await.result(futureResult, 15.seconds)

}

trait TemplateSpec extends WordSpec with Matchers with ScalatestRouteTest with MainContext with BeforeAndAfter {

  override def afterAll {
    database.close()
  }

}
