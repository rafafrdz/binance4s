package io.github.rafafrdz.binance.examples

import cats.effect._
import io.circe.Json
import io.github.rafafrdz.binance.BinanceTask
import io.github.rafafrdz.binance.api.function.query._
import io.github.rafafrdz.binance.api.function.uri._
import io.github.rafafrdz.binance.api.implicits._
import io.github.rafafrdz.binance.api.uri.BinanceURI
import io.github.rafafrdz.binance.client.BinanceClient
import org.http4s.circe.CirceEntityCodec._

object BinanceGetEx extends IOApp.Simple {

  lazy val bnc: BinanceClient =
    BinanceClient.build.test.credential.create()

  /** Ping */
  val ping: BinanceTask[BinanceURI] =
    ("api" / "v3" / "ping").?

  /** Check Server Time */
  val checkServerTime: BinanceTask[BinanceURI] =
    ("api" / "v3" / "time").?

  /** All Coins' Information */
  lazy val allCoinsInformation: BinanceTask[BinanceURI] =
    (sapi \ v1 \ capital \ config \ getall).?

  /** Deposit History */
  lazy val depositHistory: BinanceTask[BinanceURI] =
    sapi \ v1 \ capital \ deposit \ hisrec ? (timestamp & startTime("01/06/2022"))

  /** System Status */
  lazy val systemStatus: BinanceTask[BinanceURI] =
    sapi \ v1 \ system \ statuss ? timestamp


  override def run: IO[Unit] =
    for {
      pingUri <- bnc.run(ping)
      respPing <- bnc.get[String](ping)
      respCheck <- bnc.get[String](checkServerTime)
      _ <- IO.println(pingUri.show())
      _ <- IO.println(respPing)
      _ <- IO.println(respCheck)
      dailyAccountSnapshotUri <- bnc.run(allCoinsInformation)
      _ <- IO.println(dailyAccountSnapshotUri.show())
      respDaily <- bnc.get[Json](depositHistory)
      _ <- IO.println(respDaily)
    } yield ()
}
