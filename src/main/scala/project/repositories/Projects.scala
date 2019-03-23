package project.repositories

import project.domain.{Project, ProjectId, ProjectName}
import slick.jdbc.PostgresProfile.api._
import slick.lifted.ProvenShape

object Projects {
  implicit val projectIdImpl: BaseColumnType[ProjectId] = MappedColumnType.base(_.id, ProjectId.apply)
  implicit val projectNameImpl: BaseColumnType[ProjectName] = MappedColumnType.base(_.name, ProjectName.apply)
}

class Projects(tag: Tag) extends Table[Project](tag, "Projects") {
  import Projects._

  def id: Rep[ProjectId] = column[ProjectId]("id", O.PrimaryKey)

  def name: Rep[ProjectName] = column[ProjectName]("name")

  def * : ProvenShape[Project] = (id, name) <> (Project.tupled, Project.unapply)
}
