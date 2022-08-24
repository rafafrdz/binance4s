package dev.rafafrdz.binance.app

import dev.rafafrdz.binance.BinanceOptionTask
import dev.rafafrdz.binance.api.options.QueryRequest
import dev.rafafrdz.binance.api.reqest.get._
import dev.rafafrdz.binance.config.BinanceConfig
import dev.rafafrdz.binance.session.{BinanceContext, BinanceContextBuilder}
import dev.rafafrdz.binance.session.options.BinanceAPIUri
import dev.rafafrdz.binance.session.security.Credential
import requests.Response

object Example {

  val bconf: BinanceConfig =
    BinanceConfig.getFrom("config/application")

  val credential: Credential =
    Credential.from(bconf)

//  val bcntx: BinanceContext =
//    BinanceContext
//      .build()
//      .setCredentials(credential)
//      .create()

  val bcntxBuilder =
    BinanceContext
      .build()
      .default()
      .api("test")
//      .from("config/application")
//      .create()

  val bcntx = bcntxBuilder.create()

  val currentAPI: BinanceAPIUri = bcntx.api()

  val currentCredentials: Credential = bcntx.credential()

  val queryDepositHistory: BinanceOptionTask[QueryRequest] =
    QueryRequest
      .build()
      .timestamp()
      .signature

  val responseDepositHistory: BinanceOptionTask[Response] =
    depositHistory(queryDepositHistory)

  val queryCurrentAVGPrice: BinanceOptionTask[QueryRequest] =
    QueryRequest
      .build()
      .symbol("BNBUSDT")
      .formalize()

  val responseCurrentAVGPrice: BinanceOptionTask[Response] =
    currentAVGPrice(queryCurrentAVGPrice)

  val queryConvertTradeHistory: BinanceOptionTask[QueryRequest] =
    QueryRequest
      .build()
      .startTime("20/08/2022 17:15:00")
      .endTime("2022/08/24")
      //      .startTime(1654378632000L)
      //      .endTime(1661292000000L)
      .signature



  val responseConvertTradeHistory: BinanceOptionTask[Response] =
    convertTradeHistory(queryConvertTradeHistory)


  def main(args: Array[String]): Unit = {
    println(responseDepositHistory(bcntx))
    println(responseCurrentAVGPrice(bcntx))
    println(responseConvertTradeHistory(bcntx))
  }
}
