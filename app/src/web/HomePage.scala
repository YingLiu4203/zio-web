package app.web

import zio.{Runtime, Task}
import th.logz.LoggerProvider

object homePage extends LoggerProvider {

  def render(): String = {
    logger.debug("Enter render().")
    val task: Task[String] = Task(create())
    Runtime.global
    // Runtime.default
      .unsafeRunSync(task)
      .fold(
        cause => {
          val throwable = cause.squashTrace
          logger.error("Homepage throwed an exception.", throwable)
          throw throwable
        },
        result => {
          logger.debug("Home page rendered")
          result
        }
      )
  }

  def create(): String = {
    import scalatags.Text.all.{getClass => getClz, _}

    logger.debug(s"Enter create()")
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
            p("Messages for today.")
          )
        )
      )
    ).render

    // throw new RuntimeException("something tested")
  }

}
