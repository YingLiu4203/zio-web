package app

import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.{ErrorHandler, StaticHandler}

import th.logz.LoggerProvider

import app.appConfig.AppConfig

object mainRouter extends LoggerProvider {

  private val DevEnvironment = "development"
  private val WebRoot = "app/resources/public"

  def setRoutes(router: Router, config: AppConfig) = {
    val homeRoute = router.get("/")

    homeRoute.handler(rc => {
      val response = rc.response()
      response.end(web.homePage.render())
    })

    router
      .route("/static/*")
      .handler(StaticHandler.create().setWebRoot(WebRoot))

    val failureRoute = router.route().last()
    failureRoute.failureHandler(ErrorHandler.create(isDev))

    logger.debug("Routes are set.")

    def isDev = {
      val appEnvironment = config.appEnvironment
      logger.debug(s"app environment: ${appEnvironment}")
      if (appEnvironment.toLowerCase() == DevEnvironment) {
        true
      } else false
    }
  }
}
