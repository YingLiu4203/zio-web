package app.util

import zio.{Runtime, Task}

import th.logz.LoggerProvider

object runZ extends LoggerProvider {
  private val runtime = Runtime.default

  def runTask[A](task: Task[A]): A = {
    val id = task.toString
    logger.debug(s"Run task id:${id}.")

    val forked = for {
      fiber <- task.fork
      result <- fiber.join
    } yield result

    runtime
      .unsafeRunSync(forked)
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
