package app

import pureconfig._
import pureconfig.generic.auto._

object appConfig {

  case class AppConfig(
      httpPort: Int = 8080,
      appEnvironment: String = "Production"
  )

  def load(): AppConfig = ConfigSource.default.loadOrThrow[AppConfig]
}
