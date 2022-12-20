package binance1.examples

import io.github.rafafrdz.binance._
import io.github.rafafrdz.binance1.api.reqest.get._
import io.github.rafafrdz.binance1.api.options.QueryRequest
import io.github.rafafrdz.binance1.core.session.BinanceContext
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
