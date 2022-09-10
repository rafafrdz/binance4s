package io.github.rafafrdz.binance.examples

import io.github.rafafrdz.binance._
import io.github.rafafrdz.binance.api.options.QueryRequest
import io.github.rafafrdz.binance.api.reqest.get._
import io.github.rafafrdz.binance.core.session.BinanceContext
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
