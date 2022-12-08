package com.kompreneble.fs2tapirexample.core

import cats.effect.IO
import fs2.io.file.{Files, Flag, Flags, Path}
import fs2.{Pipe, Stream}
import io.circe.Decoder.Result
import io.circe.Json
import scala.concurrent.duration._

import java.nio.charset.Charset

object write {


  def jsonToFile: Pipe[IO, Json, Nothing] = { input =>

    def filename(
      json: Json
    ): Result[Path] = json.hcursor.downField("id").as[Int].map(s => Path("posts") / s"$s.json")

    input
      //      .chunkLimit(1).unchunks
      .map(json => filename(json) match {
        case Left(err) =>
          Stream.eval(IO.println(err.toString())).drain
        case Right(filename) =>
          Stream.emit[IO, String](json.noSpaces)
//            .evalTap(_ => IO.println(s"starting ${filename.absolute}"))
//            .evalTap(_ => IO.sleep(1.second))
//            .evalTap(_ => IO.println(s"done ${filename.absolute}"))
            .through(fs2.text.encode(Charset.forName("UTF-8")))
            .through(Files[IO].writeAll(filename, Flags(Flag.Create)))
      }).parJoin(6)


  }

}
