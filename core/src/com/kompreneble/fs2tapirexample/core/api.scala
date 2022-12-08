package com.kompreneble.fs2tapirexample.core

import cats.effect.IO
import io.circe.Json
import sttp.capabilities.fs2.Fs2Streams
import sttp.tapir.json.circe._
import sttp.tapir._

object api {

  val getPostsByIndex: Endpoint[Unit, Int, Unit, Json, Any] =
    endpoint
      .get.in("posts" / path[Int])
      .out(jsonBody[Json])

  val getPostsAll: Endpoint[Unit, Unit, Unit, fs2.Stream[IO, Byte], Fs2Streams[IO]] =
    endpoint
      .get.in("posts")
      .out(streamTextBody(Fs2Streams[IO])(CodecFormat.Json()))


}
