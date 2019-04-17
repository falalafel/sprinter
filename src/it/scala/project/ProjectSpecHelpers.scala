package project

import java.time.LocalDate

import project.domain.{ProjectClosingStatus, ProjectCreate, ProjectName, ProjectStartDate, ProjectUpdate, SprintDuration}

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

object ProjectSpecHelpers {

  def result[T](futureResult: Future[T]): T =
    Await.result(futureResult, 5.seconds)

  def testProjectCreate = ProjectCreate(ProjectName("xd"), ProjectStartDate(LocalDate.now()), SprintDuration(7))

  def testProjectUpdate(projectName: ProjectName, projectClosingStatus: ProjectClosingStatus) =
    ProjectUpdate(Some(projectName), Some(projectClosingStatus))

}
