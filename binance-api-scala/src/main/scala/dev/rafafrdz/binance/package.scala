package dev.rafafrdz

import dev.rafafrdz.binance.session.BinanceContext

package object binance {
  type BinanceTask[S] = BinanceContext => S
  type BinanceOptionTask[S] = BinanceTask[Option[S]]

}
