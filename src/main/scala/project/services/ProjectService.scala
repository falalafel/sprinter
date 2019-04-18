package project.services

import project.domain.{Project, ProjectId, ProjectUpdate}
import project.storages.ProjectStorage
import slick.jdbc.PostgresProfile.api._
import com.rms.miu.slickcats.DBIOInstances._
import cats.data.OptionT
import scala.concurrent.{ExecutionContext, Future}

class ProjectService(db: Database, projectStorage: ProjectStorage)
                    (implicit ec: ExecutionContext){

  def getProjects: Future[Seq[Project]] =
    db.run(projectStorage.getAllProjects)

  def getProject(projectId: ProjectId): Future[Option[Project]] =
    db.run(projectStorage.getProjectById(projectId))

  def createProject(project: Project): Future[ProjectId] =
    db.run(projectStorage.insertProject(project).map(_ => project.projectId))

  def updateProject(projectId: ProjectId, projectUpdate: ProjectUpdate): Future[Option[Int]] = {
    val query = for {
      project <- OptionT(projectStorage.getProjectById(projectId))
      updatedProject = projectUpdate.update(project)
      result <- OptionT.liftF(projectStorage.updateProject(updatedProject))
    } yield result

    db.run(query.value)
  }

  def deleteProject(projectId: ProjectId): Future[Option[Int]] = {
    val query = for {
      _ <- OptionT(projectStorage.getProjectById(projectId))
      result <- OptionT.liftF(projectStorage.deleteProject(projectId))
    } yield result

    db.run(query.value)
  }

}
