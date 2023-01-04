package io.github.rafafrdz.binance.request

import io.github.rafafrdz.binance.BinanceTask
import io.github.rafafrdz.binance.api.function.uri._
import io.github.rafafrdz.binance.api.uri.BinanceURI

/** [DOC] https://github.com/binance/binance-spot-api-docs/blob/master/rest-api.md#general-endpoints */
object GeneralEndpoints {

  /**
   * Test Connectivity
   *
   * GET /api/v3/ping
   * [DOC] https://github.com/binance/binance-spot-api-docs/blob/master/rest-api.md#test-connectivity
   * */
  val testConnectivity: BinanceTask[BinanceURI] =
    (api \ v3 \ ping).?

  /**
   * Check Server Time
   *
   * GET /api/v3/time
   * [DOC] https://github.com/binance/binance-spot-api-docs/blob/master/rest-api.md#check-server-time
   */
  val checkServerTime: BinanceTask[BinanceURI] =
    (api \ v3 \ time).?

  /**
   * Exchange Information
   *
   * GET /api/v3/exchangeInfo
   * [DOC] https://github.com/binance/binance-spot-api-docs/blob/master/rest-api.md#exchange-information
   */
  val exchangeInformation: BinanceTask[BinanceURI] =
    (api \ v3 \ exchangeInfo).?
}
