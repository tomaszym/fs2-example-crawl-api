package com.kompreneble.fs2tapirexample.console

import com.kompreneble.fs2tapirexample.core.{fetch, write}
import sttp.model.Uri
import cats.syntax.all._
import cats.effect._
import fs2.Stream
import sttp.client3.UriContext
import sttp.client3.httpclient.fs2.HttpClientFs2Backend

object ConsoleMain extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = {

    val S = for {

      baseUri <- Stream.eval(IO.fromEither(
        args.headOption.toRight("no uri argument provided at all").flatMap(stringUri=>
          Uri.parse(stringUri)
        ).leftMap(new IllegalArgumentException(_))
      )).evalTap(IO.println)

      backend <- Stream.resource(HttpClientFs2Backend.resource[IO]())

      jsons <- Stream.eval(fetch.all(baseUri, backend))

      _ <- jsons.through(write.jsonToFile)

    } yield ()


    S.compile.drain.as(ExitCode.Success)
  }
}