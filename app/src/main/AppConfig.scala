package app

import pureconfig.ConfigSource
import pureconfig.generic.auto._ // required for case class or sealed trait

object appConfig {

  case class AppConfig(
      httpPort: Int = 8080,
      appEnvironment: String = "Production"
  )

  def load(): AppConfig = ConfigSource.default.loadOrThrow[AppConfig]
}
