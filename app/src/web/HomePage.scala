package app.web

import th.logz.LoggerProvider
import zio.Task

import app.util.{runZ, pageHelper}
import io.vertx.ext.web.RoutingContext

object homePage extends LoggerProvider {
  def handle(rc: RoutingContext): Unit = {
    logger.debug("Enter handle().")
    val page = runZ.runTask(renderHome)
    pageHelper.renderHtml(rc, page)
  }

  val renderHome: Task[String] = {
    Task {
      logger.debug("Enter renderHome")

      val sleepTime = 50 * 1_000_000L
      val startTime = System.nanoTime()
      while ((System.nanoTime() - startTime) < sleepTime) {}

      import scalatags.Text.all.{getClass => getClazz, _}
      html(
        head(
          meta(charset := "utf-8"),
          link(
            rel := "stylesheet",
            href := "/static/bootstrap-4.5.0-dist/css/bootstrap.min.css"
          )
        ),
        body(
          div(cls := "container")(
            h1("Scala Chat 聊天"),
            hr,
            div(id := "messageList")(
              p("Have a nice day.")
            )
          )
        )
      ).render
    }
  }
}
