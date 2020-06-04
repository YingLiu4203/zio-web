package th.logz

import com.tersesystems.blindsight.{Logger, LoggerFactory}

import zio.{Has, UIO, ZIO, ULayer, ZLayer}

case class ZLogger(logger: Logger) {
  def info(message: String): UIO[Unit] = UIO(logger.info(message))
  def debug(message: String): UIO[Unit] = UIO(logger.debug(message))
}

/**
  * Defines `logger` as a lazy value initialized with an underlying `org.slf4j.Logger`
  * named according to the class into which this trait is mixed.
  */
trait LoggerProvider {

  @transient
  protected lazy val logger: Logger = LoggerFactory.getLogger(getClass)
}

/**
  * Defines `logger` as a lazy value initialized with an underlying `org.slf4j.Logger`
  * named according to the class into which this trait is mixed.
  */
trait ZLoggerProvider {
  @transient
  lazy val zLog: ZLogger = ZLogger(LoggerFactory.getLogger(getClass))

}

// defined a service to customize logger name
class LogZ {
  def getLogger(name: String): ZLogger = ZLogger(LoggerFactory.getLogger(name))
}

object LogZ {
  val live: ULayer[Has[LogZ]] = ZLayer.succeed(new LogZ)
  // access methods
  def getLogger(name: String): ZIO[Has[LogZ], Nothing, ZLogger] =
    ZIO.access(_.get.getLogger(name))
}
