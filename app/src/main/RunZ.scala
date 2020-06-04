package app

import zio.{Runtime, Task}

import th.logz.LoggerProvider

object runZ extends LoggerProvider {
  private val runtime = Runtime.global

  def runTask[A](task: Task[A]): A = {
    val id = task.toString
    logger.debug(s"Run task id:${id}.")
    runtime
      .unsafeRunSync(task)
      .fold(
        cause => {
          val throwable = cause.squashTrace
          logger.error(s"Run failed for task id:${id}.", throwable)
          throw throwable
        },
        result => {
          logger.debug(s"Run completed for task id:${id}.")
          result
        }
      )
  }
}
