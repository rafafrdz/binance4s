package io.github.rafafrdz.binance.examples

import cats.effect._
import io.circe.Json
import io.github.rafafrdz.binance.BinanceTask
import io.github.rafafrdz.binance.api.function.query._
import io.github.rafafrdz.binance.api.function.uri._
import io.github.rafafrdz.binance.api.implicits._
import io.github.rafafrdz.binance.api.uri.BinanceURI
import io.github.rafafrdz.binance.client.BinanceClient
//import org.http4s.circe.CirceEntityCodec._

object BinanceGetEx extends IOApp.Simple {

  lazy val bnc: BinanceClient =
    BinanceClient.build.api.credential.create()

  /** Ping */
  val ping: BinanceTask[BinanceURI] =
    ("api" / "v3" / "ping").?

  /** Check Server Time */
  val checkServerTime: BinanceTask[BinanceURI] =
    ("api" / "v3" / "time").?

  /** All Coins' Information */
  lazy val allCoinsInformation: BinanceTask[BinanceURI] =
    (sapi \ v1 \ capital \ config \ getall) ? timestamp

  /** Deposit History */
  lazy val depositHistory: BinanceTask[BinanceURI] =
    sapi \ v1 \ capital \ deposit \ hisrec ? (timestamp & startTime & endTime)

  /** Withdraw History */
  lazy val withdrawHistory: BinanceTask[BinanceURI] =
    sapi \ v1 \ capital \ withdraw \ history ? (timestamp & startTime & endTime)

  /** Account API Trading Status */
  lazy val accountAPITradingStatus: BinanceTask[BinanceURI] =
    sapi \ v1 \ account \ apiTradingStatus ? timestamp

  /** Daily Account Snapshot */
  lazy val dailyAccountSnapshot: BinanceTask[BinanceURI] =
    sapi \ v1 \ accountSnapshot ? (timestamp & _type("SPOT"))

  /** Account Status */
  lazy val accountStatus: BinanceTask[BinanceURI] =
    sapi \ v1 \ account \ statuz ? timestamp

  /** System Status */
  lazy val systemStatus: BinanceTask[BinanceURI] =
    sapi \ v1 \ system \ statuz ? timestamp


  /** In order to parse a Json (Circe), it needs import org.http4s.circe.CirceEntityCodec._ */
  override def run: IO[Unit] =
    for {
      pingUri <- bnc.run(ping)
      _ <- IO.println(pingUri)
      respPing <- bnc.get[String](ping)
      _ <- IO.println(respPing)
      respCheck <- bnc.get[String](checkServerTime)
      _ <- IO.println(respCheck)
      allCoinsInformationUri <- bnc.run(allCoinsInformation)
      _ <- IO.println(allCoinsInformationUri)
      respDaily <- bnc.get[String](dailyAccountSnapshot)
      _ <- IO.println(respDaily)
    } yield ()
}
