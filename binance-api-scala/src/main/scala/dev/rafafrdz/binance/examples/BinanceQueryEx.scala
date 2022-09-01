package dev.rafafrdz.binance.examples

import dev.rafafrdz.binance.BinanceQuery
import dev.rafafrdz.binance.api.options.QueryRequest

/** This is an example where show how to create a BinanceQuery in some ways */
object BinanceQueryEx {

  /**
   * [DOC] https://binance-docs.github.io/apidocs/spot/en/#deposit-history-supporting-network-user_data
   * timestamp={{timestamp}}&signature={{signature}}
   */
  val queryDepositHistory: BinanceQuery =
    QueryRequest
      .build()
      .timestamp()
      .signature

  /**
   * [DOC] https://binance-docs.github.io/apidocs/spot/en/#current-average-price
   * symbol={{symbol}}
   */

  val queryCurrentAVGPrice: BinanceQuery =
    QueryRequest
      .build()
      .symbol("BNBUSDT")
      .formalize()

  /**
   * [DOC] https://binance-docs.github.io/apidocs/spot/en/#get-convert-trade-history-user_data
   * startTime={{startTime}}&endTime={{endTime}}&timestamp={{timestamp}}&signature={{signature}}
   */

    /** Style 1 */
  val queryConvertTradeHistory: BinanceQuery =
    QueryRequest
      .build()
      .startTime("20/08/2022 17:15:00") /** dd/mm/yyyy HH:mm:ss */
      .endTime("24/08/2022") /** dd/mm/yyyy */
      .timestamp()
      .signature


  /** Style 2 */
  val queryConvertTradeHistory2: BinanceQuery =
    QueryRequest
      .build()
      .startTime(1654378632000L) /** Milliseconds */
      .endTime(1661292000000L) /** Milliseconds */
      .timestamp()
      .signature

  /** Style 3 */
  val queryConvertTradeHistory3: BinanceQuery =
    QueryRequest
      .build()
      .startTime("20-08-2022 17:15:00") /** dd-mm-yyyy HH:mm:ss */
      .endTime("24-08-2022") /** dd-mm-yyyy */
      .timestamp()
      .signature

  /** Style 4 */
  val queryConvertTradeHistory4: BinanceQuery =
    QueryRequest
      .build()
      .startTime("2022/08/20 17:15:00") /** yyyy/mm/dd HH:mm:ss */
      .endTime("2022-08-24") /** yyyy-mm-yy */
      .timestamp()
      .signature

  /** Style 5 */
  val queryConvertTradeHistory5: BinanceQuery =
    QueryRequest
      .build()
      .startTime("20/08/2022 17:15:00") /** dd/mm/yyyy HH:mm:ss */
      .endTime("2022-08-24") /** yyyy-mm-yy */
      .timestamp()
      .signature
}
