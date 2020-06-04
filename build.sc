// for bloop work properly in IDE
import $ivy.`com.lihaoyi::mill-contrib-bloop:$MILL_VERSION`

import mill.Agg
import mill.scalalib.{DepSyntax, ScalaModule}

trait Common extends ScalaModule {
  def scalaVersion = "2.13.2"

  import coursier.maven.MavenRepository

  def repositories = super.repositories ++ Seq(
    MavenRepository(
      "https://dl.bintray.com/tersesystems/maven"
    )
  )

  def ivyDeps = Agg(ivy"dev.zio::zio:1.0.0-RC20")

}

object app extends Common {

  def moduleDeps = Seq(logz)

  def ivyDeps = Agg(
    ivy"dev.zio::zio:1.0.0-RC20",
    ivy"io.vertx:vertx-web:3.9.1",
    ivy"com.lihaoyi::scalatags:0.8.6",
    ivy"com.github.pureconfig::pureconfig:0.12.3",
    ivy"com.typesafe.scala-logging::scala-logging:3.9.2",
    ivy"com.tersesystems.blindsight::blindsight-logstash:1.0.1",
    ivy"ch.qos.logback:logback-classic:1.2.3"
  )
}

object logz extends Common {

  def ivyDeps = Agg(
    ivy"dev.zio::zio:1.0.0-RC20",
    ivy"org.slf4j:slf4j-api:1.7.30",
    ivy"com.tersesystems.blindsight::blindsight-logstash:1.0.1"
  )
}
