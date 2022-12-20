package io.github.rafafrdz.binance.examples


import io.github.rafafrdz.binance.BinanceTask
import io.github.rafafrdz.binance.api.query.BinanceQuery
import io.github.rafafrdz.binance.client.BinanceClient

object BinanceAppEx {

  /** BinanceContext */
  val bcntx: BinanceClient =
    BinanceClient
      .build
      .credential
      .test
      .create()

  /** BinanceQuery */
  val query: BinanceTask[BinanceQuery] =
    BinanceQuery
      .build
      .timestamp.?


}
