package io.github.rafafrdz

import cats.effect.IO
import io.github.rafafrdz.binance2.client.BinanceClient

package object binance2 {

  type BinanceTask[T] = BinanceClient => IO[T]
}
