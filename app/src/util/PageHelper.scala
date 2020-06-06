package app.util

import io.vertx.ext.web.RoutingContext

object pageHelper {

  def renderHtml(rc: RoutingContext, page: String) = {
    val response = rc.response()
    response.putHeader("content-type", "text/html")
    response.end(page)
  }

}
