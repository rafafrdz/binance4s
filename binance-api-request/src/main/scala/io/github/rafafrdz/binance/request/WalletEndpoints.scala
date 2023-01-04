package io.github.rafafrdz.binance.request

import io.github.rafafrdz.binance.BinanceTask
import io.github.rafafrdz.binance.api.function.query._
import io.github.rafafrdz.binance.api.function.uri._
import io.github.rafafrdz.binance.api.uri.BinanceURI

object WalletEndpoints {

  /**
   * System Status (System)
   * GET /sapi/v1/system/status
   * [DOC] https://binance-docs.github.io/apidocs/spot/en/#system-status-system
   * */
  val systemStatus: BinanceTask[BinanceURI] =
    (sapi \ v1 \ system \ statuz).?

  /**
   * All Coins' Information
   * GET /sapi/v1/capital/config/getall
   * [DOC] https://binance-docs.github.io/apidocs/spot/en/#all-coins-39-information-user_data
   * */
  val allCoinsInformation: BinanceTask[BinanceURI] =
    (sapi \ v1 \ capital \ config \ getall) ? timestamp

  /**
   * Daily Account Snapshot
   * Require: `type`
   * GET /sapi/v1/accountSnapshot
   * [DOC] https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data
   * */
  val dailyAccountSnapshot: BinanceTask[BinanceURI] =
    sapi \ v1 \ accountSnapshot ? timestamp

}
