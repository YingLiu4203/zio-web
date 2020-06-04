package app

import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.{ErrorHandler, StaticHandler}

import th.logz.LoggerProvider

import app.appConfig.AppConfig
import app.web.appRoutes

object mainRouter extends LoggerProvider {

  private val DevEnvironment = "development"
  private val WebRoot = "app/resources/public"

  def setRoutes(router: Router, config: AppConfig) = {
    appRoutes.setRoutes(router)
    setSystemRoutes(router, config)
  }

  private def setSystemRoutes(router: Router, config: AppConfig) {
    router
      .route("/static/*")
      .handler(StaticHandler.create().setWebRoot(WebRoot))

    val failureRoute = router.route().last()
    failureRoute.failureHandler(ErrorHandler.create(isDev(config)))
    logger.debug("Static and failure routes are set.")
  }

  private def isDev(config: AppConfig) = {
    val appEnvironment = config.appEnvironment
    logger.debug(s"Application environment: ${appEnvironment}")
    if (appEnvironment.toLowerCase() == DevEnvironment) {
      true
    } else false
  }
}
