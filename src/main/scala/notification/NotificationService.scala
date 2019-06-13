package notification

import akka.actor.{ActorSystem, Cancellable}
import com.typesafe.config.Config
import sprint.services.SprintService
import user.services.UserService

import scala.concurrent.duration._
import courier._
import declaration.services.DeclarationService
import projectmembership.services.ProjectMembershipService
import user.domain.Password

import scala.concurrent.ExecutionContext

class UserCreationService(system: ActorSystem,
                          config: Config)(implicit ec: ExecutionContext) {
  val senderName = config.getString("mailer.name")
  val senderPass = config.getString("mailer.password")
  val subject = "SPRINTER: declaration reminder"
  val content = Text("Did you forgot to declare your availability for next sprint? Sprinter is waiting for your declaration.")
  def content2(pass: String) = Text(s"Hello form Sprinter! Your password is: $pass")
  val mailer = Mailer("smtp.gmail.com", 587)
    .auth(true)
    .as(senderName, senderPass)
    .startTls(true)()

  def sendMail(recipient: String, password: Password): Unit = {
    mailer(Envelope.from(senderName.addr)
      .to(recipient.addr)
      .subject(subject)
      .content(content2(password.password)))
      .onComplete {
        _ => println(s"Mailed to $recipient")
      }
  }
}

class NotificationService(userService: UserService,
                          sprintService: SprintService,
                          declarationService: DeclarationService,
                          projectMembershipService: ProjectMembershipService,
                          system: ActorSystem,
                          config: Config)(implicit ec: ExecutionContext) {

  val senderName = config.getString("mailer.name")
  val senderPass = config.getString("mailer.password")
  val subject = "SPRINTER: declaration reminder"
  val content = Text("Did you forgot to declare your availability for next sprint? Sprinter is waiting for your declaration.")
  def content2(pass: String) = Text(s"Hello form Sprinter! Your password is: $pass")
  val mailer = Mailer("smtp.gmail.com", 587)
    .auth(true)
    .as(senderName, senderPass)
    .startTls(true)()

  def run: Cancellable = system.scheduler.schedule(3 hours, 1 days) {
    sprintService.getOpenSprints.map {
      _.map { sprint =>
        for {
          declarations <- declarationService.getDeclarationsForSprint(sprint.projectId, sprint.sprintId)
          memberships <- projectMembershipService.getProjectMembershipsForProject(sprint.projectId)
        } yield {
          val userIds = memberships.map(_.userId).filter(userId => !declarations.map(_.userId).contains(userId))

          userIds.map(userService.getUser(_).map(_.foreach(user => sendMail(user.mail.mail))))
        }
      }
    }
  }

  def sendMail(recipient: String): Unit = {
    mailer(Envelope.from(senderName.addr)
      .to(recipient.addr)
      .subject(subject)
      .content(content))
      .onComplete {
        _ => println(s"Mailed to $recipient")
      }
  }
}
