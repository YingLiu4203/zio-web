package app.web

import th.logz.LoggerProvider
import zio.Task

import app.runZ

object homePage extends LoggerProvider {
  def render(): String = {
    logger.debug("Enter render().")
    runZ.runTask(create())
  }

  def create(): Task[String] = {
    Task {
      logger.debug("Enter create()")

      // Thread.getAllStackTraces().keySet().forEach((t) => println(t.getName()))

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
