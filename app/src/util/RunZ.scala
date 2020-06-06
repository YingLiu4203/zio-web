package app.util

import zio.{Runtime, Task, blocking}

import th.logz.LoggerProvider
import io.vertx.ext.web.RoutingContext

object runZ extends LoggerProvider {
  private val runtime = Runtime.default

  def runTask(task: Task[String], rc: RoutingContext): Unit = {
    val id = task.toString
    logger.debug(s"Run task id:${id}.")

    val forked = for {
      fiber <- task.fork
      result <- fiber.join
    } yield result

    // val blocked = blocking.blocking(task)

    runtime.unsafeRunAsync(forked)(
      _.fold(
        cause => {
          val throwable = cause.squashTrace
          logger.error(s"Run failed for task id:${id}.", throwable)
          throw throwable
        },
        result => {
          logger.debug(s"Run completed for task id:${id}.")
          pageHelper.renderHtml(rc, result)
        }
      )
    )
  }
}
