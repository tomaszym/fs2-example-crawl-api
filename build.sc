import mill._
import scalalib._

object core extends ScalaModule {
  def scalaVersion = "2.13.10"

  def ivyDeps = Agg(
    ivy"com.softwaremill.sttp.tapir::tapir-sttp-client:1.2.3",
    ivy"com.softwaremill.sttp.tapir::tapir-json-circe:1.2.3",
    ivy"com.softwaremill.sttp.client3::fs2:3.8.5",
    ivy"io.circe::circe-fs2:0.14.0",
    ivy"co.fs2::fs2-scodec:3.4.0",
  )

}

object console extends ScalaModule {
  def scalaVersion = "2.13.10"

  def moduleDeps = Seq(core)
}