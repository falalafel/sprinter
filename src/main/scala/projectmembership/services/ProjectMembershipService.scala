package projectmembership.services

import slick.jdbc.PostgresProfile.api._
import com.rms.miu.slickcats.DBIOInstances._
import cats.data.OptionT
import projectmembership.domain.ProjectMembership
import projectmembership.storages.ProjectMembershipStorage
import project.domain.ProjectId
import user.domain.UserId

import scala.concurrent.{ExecutionContext, Future}

class ProjectMembershipService(db: Database, projectMembershipStorage: ProjectMembershipStorage)
                              (implicit ec: ExecutionContext) {

  def getProjectMembershipsForProject(projectId: ProjectId): Future[Seq[ProjectMembership]] =
    db.run(projectMembershipStorage.getProjectMembershipsForProject(projectId))

  def getProjectMembership(projectId: ProjectId, userId: UserId): Future[Option[ProjectMembership]] =
    db.run(projectMembershipStorage.getProjectMembership(projectId, userId))

  def insertOrUpdateProjectMembership(projectMembership: ProjectMembership): Future[(ProjectId, UserId)] =
    db.run(projectMembershipStorage.insertOrUpdateProjectMembership(projectMembership)
      .map(_ => (projectMembership.projectId, projectMembership.userId)))

  def deleteProjectMembership(projectId: ProjectId, userId: UserId): Future[Option[Int]] = {
    val query = for {
      _ <- OptionT(projectMembershipStorage.getProjectMembership(projectId, userId))
      result <- OptionT.liftF(projectMembershipStorage.deleteProjectMembership(projectId, userId))
    } yield result

    db.run(query.value)
  }
}
