package io.github.rafafrdz.binance.examples

import io.github.rafafrdz.binance._
import io.github.rafafrdz.binance.api.query.BinanceQuery

/** This is an example where show how to create a BinanceQuery in some ways */
object BinanceQueryEx {

  /**
   * [DOC] https://binance-docs.github.io/apidocs/spot/en/#deposit-history-supporting-network-user_data
   * timestamp={{timestamp}}
   */

  /** query */
  val depositHistoryQuery: BinanceQuery =
    BinanceQuery
      .build
      .timestamp

  /** task */
  val depositHistoryTask: BinanceTask[BinanceQuery] =
    BinanceQuery
      .build
      .timestamp
      .?

  /**
   * [DOC] https://binance-docs.github.io/apidocs/spot/en/#current-average-price
   * symbol={{symbol}}
   */

    /** query */
  val currentAVGPriceQuery: BinanceQuery =
    BinanceQuery
      .build
      .symbol("BNBUSDT")

  /** task */
  val currentAVGPriceTask: BinanceTask[BinanceQuery] =
    BinanceQuery
      .build
      .symbol("BNBUSDT")
      .?

  /**
   * [DOC] https://binance-docs.github.io/apidocs/spot/en/#get-convert-trade-history-user_data
   * startTime={{startTime}}&endTime={{endTime}}&timestamp={{timestamp}}&signature={{signature}}
   */

  /** Style 1 - Using dd/MM/yyyy (hh:mm:ss) pattern */
  val convertTradeHistoryQuery: BinanceQuery =
    BinanceQuery
      .build()
      .startTime("20/08/2022 17:15:00")
      .endTime("24/08/2022")
      .timestamp


  /** Style 2 - Using milliseconds */
  val convertTradeHistory2: BinanceQuery =
    BinanceQuery
      .build()
      .startTime(1654378632000L)
      .endTime(1661292000000L)
      .timestamp

  /** Style 3 - Using dd-MM-yyyy (hh:mm:ss) pattern */
  val convertTradeHistory3: BinanceQuery =
    BinanceQuery
      .build()
      .startTime("20-08-2022 17:15:00")
      .endTime("24-08-2022")
      .timestamp

  /** Style 4 - Using both patterns */
  val convertTradeHistory4: BinanceQuery =
    BinanceQuery
      .build()
      .startTime("2022/08/20 17:15:00")
      .endTime("2022-08-24")
      .timestamp

  /** Style 5 - Using what you want */
  val convertTradeHistory5: BinanceQuery =
    BinanceQuery
      .build
      .startTime("20/08/2022 17:15:00")
      .endTime(1661292000000L)
      .timestamp
}
