package app.util

import zio.{Runtime, Task}

import th.logz.LoggerProvider
import io.vertx.ext.web.RoutingContext

object runZ extends LoggerProvider {
  private val runtime = Runtime.default

  def runTask(task: Task[String], rc: RoutingContext): Unit = {
    logger.debug("In runTask.")

    // run in the async pool
    val forked = for {
      fiber <- task.fork
      result <- fiber.join
    } yield result

    // run in blocking pool
    // import zio.blocking
    // val blocked = blocking.blocking(task)

    runtime.unsafeRunAsync(forked)(
      _.fold(
        cause => {
          val throwable = cause.squashTrace
          logger.warn(s"runTask failed.", throwable)
          rc.fail(throwable)
        },
        result => {
          logger.debug("runTask completed.")
          pageHelper.renderHtml(rc, result)
        }
      )
    )
  }
}
