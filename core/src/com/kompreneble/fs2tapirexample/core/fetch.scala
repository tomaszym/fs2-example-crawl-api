package com.kompreneble.fs2tapirexample.core

import cats.effect.IO
import com.kompreneble.fs2tapirexample.core.api._
import io.circe.Json
import sttp.capabilities.fs2.Fs2Streams
import sttp.client3.SttpBackend
import sttp.model.Uri

object fetch {

  def one(
    baseUri: Uri,
    backend: SttpBackend[IO, Any],
  ): Int => IO[Json] =
    Fs2CirceTapir
      .toClientThrowErrors(
        getPostsByIndex,
        baseUri = Some(baseUri),
        backend = backend,
      )

  def all(
    baseUri: Uri,
    backend: SttpBackend[IO, Fs2Streams[IO]],
  ): IO[fs2.Stream[IO, Json]] = {
    import io.circe.fs2._

    Fs2CirceTapir
      .toClientThrowErrors(
        getPostsAll,
        baseUri = Some(baseUri),
        backend = backend
      ).apply().map(
      _.through(byteArrayParser)
    )
  }

}
