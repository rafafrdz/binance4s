package io.github.rafafrdz.binance.api.function

import io.github.rafafrdz.binance.BinanceTask
import io.github.rafafrdz.binance.api.query.BinanceQuery
import io.github.rafafrdz.binance.api.uri.BinanceURI

object uri extends BinanceURI() {
  implicit class StringToBinanceUri(str: String) {

    private lazy val pure = BinanceURI.build.copy(path = Vector(str))

    def /(path: String): BinanceURI = pure / path

    def \(uri: BinanceURI): BinanceURI = pure \ uri

    def ?(query: BinanceQuery): BinanceTask[BinanceURI] = pure.?(query)

    def ?(query: BinanceTask[BinanceQuery]): BinanceTask[BinanceURI] = pure.?(query)
  }
}