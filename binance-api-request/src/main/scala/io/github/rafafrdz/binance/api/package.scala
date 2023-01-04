package io.github.rafafrdz.binance

import cats.effect.IO
import org.http4s.EntityDecoder

package object api {
  type EntityIODecoder[T] = EntityDecoder[IO, T]
}
