package project.services

import project.domain.Project
import project.repositories.ProjectRepository
import slick.jdbc.PostgresProfile.api._
import scala.concurrent.Future

class ProjectService(db: Database, projectRepository: ProjectRepository) {

  def getProjects: Future[Seq[Project]] =
    db.run(projectRepository.getAllProjects)

}
