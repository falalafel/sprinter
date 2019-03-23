package project.domain

case class ProjectId(id: Int) extends AnyVal
case class ProjectName(name: String) extends AnyVal

case class Project(projectId: ProjectId, name: ProjectName)
