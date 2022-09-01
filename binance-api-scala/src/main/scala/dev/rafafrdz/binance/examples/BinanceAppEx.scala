package dev.rafafrdz.binance.examples

import dev.rafafrdz.binance._
import dev.rafafrdz.binance.api.options._
import dev.rafafrdz.binance.api.reqest.get._
import dev.rafafrdz.binance.core.session._
import requests.Response

object BinanceAppEx {

  /** BinanceContext */
  val bcntx: BinanceContext =
    BinanceContext
      .build()
      .from("config/application.conf")
      .create()

  /** BinanceQuery */
  val query: BinanceQuery =
    QueryRequest
      .build()
      .timestamp()
      .signature

  /** BinanceRequest */
  val request: BinanceRequest =
    depositHistory(query)

  /** Response */
  val response: Option[Response] =
    request(bcntx)


}
