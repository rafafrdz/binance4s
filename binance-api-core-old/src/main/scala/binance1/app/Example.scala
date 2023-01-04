package binance1.app

import io.github.rafafrdz.binance._
import io.github.rafafrdz.binance.api.options._
import io.github.rafafrdz.binance1.api.reqest.get._
import io.github.rafafrdz.binance.core.config._
import io.github.rafafrdz.binance.core.session._
import io.github.rafafrdz.binance.core.session.options._
import io.github.rafafrdz.binance.core.session.security._
import io.github.rafafrdz.binance1.api.options.QueryRequest
import io.github.rafafrdz.binance1.core.config.BinanceConfig
import io.github.rafafrdz.binance1.core.session.options.BinanceAPIUri
import io.github.rafafrdz.binance1.core.session.{BinanceContext, BinanceContextBuilder}
import io.github.rafafrdz.binance1.core.session.security.Credential

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

  val queryDepositHistory: BinanceQuery =
    QueryRequest
      .build()
      .timestamp()
      .signature

  val responseDepositHistory: BinanceRequest =
    depositHistory(queryDepositHistory)

  val queryCurrentAVGPrice: BinanceQuery =
    QueryRequest
      .build()
      .symbol("BNBUSDT")
      .formalize()

  val responseCurrentAVGPrice: BinanceRequest =
    currentAVGPrice(queryCurrentAVGPrice)

  val queryConvertTradeHistory: BinanceQuery =
    QueryRequest
      .build()
      .startTime("20/08/2022 17:15:00")
      .endTime("2022/08/24")
      //      .startTime(1654378632000L)
      //      .endTime(1661292000000L)
      .signature


  val responseConvertTradeHistory: BinanceRequest =
    convertTradeHistory(queryConvertTradeHistory)


  def main(args: Array[String]): Unit = {
    println(responseDepositHistory(bcntx))
    println(responseCurrentAVGPrice(bcntx))
    println(responseConvertTradeHistory(bcntx))
  }
}
