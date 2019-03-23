package project.repositories

import project.domain.Project
import slick.jdbc.PostgresProfile.api._

class ProjectRepository {

  def getAllProjects: DBIO[Seq[Project]] = projects.result

  private val projects = TableQuery[Projects]

}
