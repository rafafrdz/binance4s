//package binance1.examples
//
//import io.github.rafafrdz.binance._
//import io.github.rafafrdz.binance1.api.reqest.get._
//
///** This is an example where show how to create a BinanceRequest in some ways */
//object BinanceRequestEx {
//
//  /**
//   * [DOC] https://binance-docs.github.io/apidocs/spot/en/#deposit-history-supporting-network-user_data
//   * /sapi/v1/capital/deposit/hisrec?timestamp={{timestamp}}&signature={{signature}}
//   * */
//
//  val requestDepositHistory: BinanceRequest =
//    depositHistory(BinanceQueryEx.queryDepositHistory)
//
//  /**
//   * [DOC] https://binance-docs.github.io/apidocs/spot/en/#current-average-price
//   * /api/v3/avgPrice?symbol={{symbol}}
//   */
//
//  val requestCurrentAVGPrice: BinanceRequest =
//    currentAVGPrice(BinanceQueryEx.queryCurrentAVGPrice)
//
//  /**
//   * [DOC] https://binance-docs.github.io/apidocs/spot/en/#get-convert-trade-history-user_data
//   * /sapi/v1/convert/tradeFlow?startTime={{startTime}}&endTime={{endTime}}&timestamp={{timestamp}}&signature={{signature}}
//   */
//
//  val requestConvertTradeHistory: BinanceRequest =
//    convertTradeHistory(BinanceQueryEx.queryConvertTradeHistory)
//}
