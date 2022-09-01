package dev.rafafrdz.binance.app

import dev.rafafrdz.binance.BinanceOptionTask
import dev.rafafrdz.binance.api.options.QueryRequest
import dev.rafafrdz.binance.api.reqest.get._
import dev.rafafrdz.binance.core.config.BinanceConfig
import dev.rafafrdz.binance.core.session.{BinanceContext, BinanceContextBuilder}
import dev.rafafrdz.binance.core.session.options.BinanceAPIUri
import dev.rafafrdz.binance.core.session.security.Credential
import requests.Response

object Example {

  val bconf: BinanceConfig =
    BinanceConfig.from("config/application")

  val bconf2: BinanceConfig =
    BinanceConfig
      .build()
      .set(
        "binance.credential.access.key" -> "a"
        , "binance.credential.secret.key" -> "s"
      ).define()



  val bcntxBuilder: BinanceContextBuilder =
    BinanceContext
      .build()
      .default()
      .api("test")

  val bcntx3: BinanceContext =
    bcntxBuilder
      .create()

  val bcntx2: BinanceContext =
    bcntxBuilder
      .from(bconf2)
      .create()

  //  val credential: Credential =
  //    Credential.from(bconf)

    val bcntx: BinanceContext =
      BinanceContext
        .build()
        .from(bconf)
        .create()

  val credB2: Credential = bcntx2.credential()

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
