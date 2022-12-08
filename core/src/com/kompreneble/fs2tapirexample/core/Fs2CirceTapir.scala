package com.kompreneble.fs2tapirexample.core

import sttp.tapir.Tapir
import sttp.tapir.client.sttp.SttpClientInterpreter
import sttp.tapir.json.circe.TapirJsonCirce

object Fs2CirceTapir extends Tapir
  with SttpClientInterpreter
  with TapirJsonCirce
