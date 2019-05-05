package projectmembership.domain

import project.domain.ProjectId
import user.domain.UserId

case class IsScrumMaster(value: Boolean) extends AnyVal

case class ProjectMembership(projectId: ProjectId,
                       userId: UserId,
                       isScrumMaster: IsScrumMaster)

case class ProjectMembershipCreate(isScrumMaster: IsScrumMaster) {
  def toProjectMembership(projectId: ProjectId,
                          userId: UserId) =
    ProjectMembership(projectId,
      userId,
      isScrumMaster)
}
