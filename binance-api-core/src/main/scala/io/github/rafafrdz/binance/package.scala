package io.github.rafafrdz

import cats.effect.IO
import io.github.rafafrdz.binance.client.BinanceClient

package object binance {

  type BinanceTask[T] = BinanceClient => IO[T]
}
