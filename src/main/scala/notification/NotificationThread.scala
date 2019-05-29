package notification

import sprint.services.SprintService
import user.services.UserService
import scala.concurrent.duration._
import scala.util.{Failure, Success}

import courier._

class NotificationThread(userService: UserService, sprintService: SprintService) extends Runnable {

  val system = akka.actor.ActorSystem("system")

  import system.dispatcher

  override def run(): Unit = {

    //sendMail("lukasz.kulig95@gmail.com")

    //TODO: change interval
    system.scheduler.schedule(0 milliseconds, 2 seconds) {
      sprintService.getOpenSprints onComplete {
        case Success(sprints) =>
          for (sprint <- sprints) {
            sprintService.getUsersWithNoDeclaration(sprint.sprintId) onComplete {
              case Success(users) =>
                for (user <- users) {
                  //println(sprint.sprintId + " " + user)
                  sendMail(user.mail.mail)
                }
              case Failure(t) => println("An error has occurred: " + t.getMessage)
            }
          }

        case Failure(t) => println("An error has occurred: " + t.getMessage)
      }
    }
  }

  def sendMail(recipient: String): Unit = {

    val senderName = "sprinter.fresh.delivery"
    val senderMailService = "gmail.com"
    val senderPass = "sprinter2137"
    val recipientName = recipient.split("@")(0)
    val recipientMailService = recipient.split("@")(1)

    //TODO: complete text
    val text = Text("elo mordo")


    //TODO: czemu to nie działa xD
    val mailer = Mailer("smtp.gmail.com", 587)
      .auth(true)
      .as(senderName + "@" + senderMailService, senderPass)
      .startTls(true)()

    mailer(Envelope.from((senderName + '@' + senderMailService).addr)
      .to((recipientName + '@' +recipientMailService).addr)
      .subject("SPRINTER: declaration reminder")
      .content(text)).onComplete {
      case _ => println("Mailed to " + recipient)
    }
  }
}
