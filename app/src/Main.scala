package app

import th.logz.LoggerProvider

import io.vertx.core.{AsyncResult, Handler, Vertx}
import io.vertx.core.http.HttpServer
import io.vertx.ext.web.Router

object main extends LoggerProvider {

  def main(args: Array[String]): Unit = {
    logger.debug("Application starts.")

    val config = appConfig.load()
    logger.debug("Configuration loaded.")

    // Vertx is the control center
    val vertx = Vertx.vertx()

    val router = Router.router(vertx)
    mainRouter.setRoutes(router, config)

    val server = vertx.createHttpServer()
    val httpPort = config.httpPort
    server.requestHandler(router).listen(httpPort, listenHandler)

    def listenHandler: Handler[AsyncResult[HttpServer]] =
      ready => {
        if (ready.failed()) {
          val cause = ready.cause()
          logger.error("Failed to start http server.", cause)
          throw new RuntimeException(cause)
        } else {
          logger.info(s"Http Server is listening on port ${httpPort}")
        }
      }
  }

}
