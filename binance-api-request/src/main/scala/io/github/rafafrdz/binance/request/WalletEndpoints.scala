package io.github.rafafrdz.binance.request

import io.github.rafafrdz.binance.api.function.uri._
import io.github.rafafrdz.binance.api.uri.BinanceURI

object WalletEndpoints {

  val systemStatus: BinanceURI =
    sapi \ v1 \ system \ statuz

}
