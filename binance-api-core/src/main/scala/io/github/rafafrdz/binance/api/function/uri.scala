package io.github.rafafrdz.binance.api.function

import io.github.rafafrdz.binance.BinanceTask
import io.github.rafafrdz.binance.api.query.BinanceQuery
import io.github.rafafrdz.binance.api.uri.BinanceUri

object uri extends BinanceUri() {
  implicit class StringToBinanceUri(str: String) {

    private lazy val pure = BinanceUri.build.copy(path = Vector(str))

    def /(path: String): BinanceUri = pure / path

    def \(uri: BinanceUri): BinanceUri = pure \ uri

    def ?(query: BinanceQuery): BinanceTask[BinanceUri] = pure.?(query)

    def ?(query: BinanceTask[BinanceQuery]): BinanceTask[BinanceUri] = pure.?(query)
  }
}