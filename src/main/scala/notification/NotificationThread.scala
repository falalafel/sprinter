package notification

import project.domain.ProjectId
import sprint.domain.SprintId
import sprint.storages.SprintStorage

class NotificationThread extends Runnable {
  override def run(): Unit =
    while(true){
      Thread.sleep(10000)
      // TODO: foreach open sprint
      val ss = new SprintStorage
      val res = ss.getUsersWithNoDeclaration(new SprintId(4))
      // TODO: test query
      // TODO: send messages
    }
}
