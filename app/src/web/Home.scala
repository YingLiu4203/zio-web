package app.web

import zio.{Runtime, Task}

object home {

  def render(): String = {
    val task: Task[String] = Task(create())
    Runtime.default.unsafeRunTask(task)
  }

  def create(): String = {
    import scalatags.Text.all._

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
