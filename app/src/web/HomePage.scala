package app.web

import th.logz.LoggerProvider
import zio.Task

import app.util.{runZ, pageHelper}
import io.vertx.ext.web.RoutingContext

object homePage extends LoggerProvider {
  def handle(rc: RoutingContext): Unit = {
    logger.debug("Enter handle().")
    runZ.runTask(renderHome, rc)
  }

  var count = 0

  val renderHome: Task[String] = {
    Task {
      logger.debug("Enter renderHome")

      // count += 1
      // if (count % 3 == 0)
      //   throw new RuntimeException("Triple number exception !")

      Thread.sleep(100)

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
