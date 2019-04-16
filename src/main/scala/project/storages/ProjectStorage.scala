package project.storages

import project.domain.{Project, ProjectId}
import slick.jdbc.PostgresProfile.api._

class ProjectStorage {
  import Projects._

  def getAllProjects: DBIO[Seq[Project]] = projects.result

  def getProjectById(projectId: ProjectId): DBIO[Option[Project]] =
    projects.filter(_.id === projectId).result.headOption

  def insertProject(project: Project): DBIO[Int] = projects += project

  def updateProject(project: Project): DBIO[Int] =
    projects.filter(_.id === project.projectId).update(project)

  def deleteProject(projectId: ProjectId): DBIO[Int] =
    projects.filter(_.id === projectId).delete

  private val projects = TableQuery[Projects]

}
