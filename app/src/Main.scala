package app

import th.logz.LoggerProvider

import io.vertx.core.{AsyncResult, Handler, Vertx}
import io.vertx.core.http.HttpServer
import io.vertx.ext.web.Router

object main extends LoggerProvider {

  def main(args: Array[String]): Unit = {
    logger.debug("Application started.")

    Thread.currentThread().setUncaughtExceptionHandler(new AppExceptionHandler)

    val config = appConfig.load()
    logger.debug("Configuration loaded.")

    // Vertx is the control center
    val vertx = Vertx.vertx()
    logger.debug("Vertx instance created.")

    val router = Router.router(vertx)
    mainRouter.setRoutes(router, config)

    val server = vertx.createHttpServer()
    listen(server, router, config)
  }

  def listen(server: HttpServer, router: Router, config: appConfig.AppConfig) {
    val httpPort = config.httpPort
    val listenHandler = new ListenHandler(httpPort)

    server.requestHandler(router).listen(httpPort, listenHandler)
  }
}

private class ListenHandler(port: Int)
    extends Handler[AsyncResult[HttpServer]]
    with LoggerProvider {
  def handle(ready: AsyncResult[HttpServer]): Unit = {
    if (ready.failed()) {
      val cause = ready.cause()
      logger.error("Failed to start http server.", cause)
      throw new RuntimeException(cause)
    } else {
      logger.info(s"Http Server is listening on port: ${port}.")
    }
  }
}

private class AppExceptionHandler
    extends Thread.UncaughtExceptionHandler
    with LoggerProvider {

  logger.debug("Set application exception handler.")

  override def uncaughtException(main: Thread, throwable: Throwable) = {
    logger.error(
      "Exception throwed in main thread. Application aborted.",
      throwable
    )

    throw throwable
  }
}
