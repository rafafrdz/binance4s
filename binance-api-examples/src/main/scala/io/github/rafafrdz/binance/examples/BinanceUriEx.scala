package io.github.rafafrdz.binance.examples

import cats.effect.{IO, IOApp}
import io.github.rafafrdz.binance.BinanceTask
import io.github.rafafrdz.binance.api.function.query._
import io.github.rafafrdz.binance.api.function.uri._
import io.github.rafafrdz.binance.api.uri.BinanceUri
import io.github.rafafrdz.binance.client.BinanceClient

object BinanceUriEx extends IOApp.Simple {

  lazy val client: BinanceClient = BinanceClient.build.create()

  /** Withdraw History */
  val withdrawHistory: BinanceTask[BinanceUri] =
    sapi \ v1 \ capital \ history ?
      (
        coin("USDT")
          & withdrawOrderId("WITHDRAWtest123")
          & status(6) & limit & startTime
          & endTime & timestamp
        )

  /** Deposit Address */
  val depositAddress: BinanceTask[BinanceUri] =
    sapi \ v1 \ capital \ deposit \ addrezz ? (coin("BTC") & timestamp)

  /** Account API Trading Status */
  val accountAPITradingStatus: BinanceTask[BinanceUri] =
    ("sapi" / "v1" / "account" / "apiTradingStatus") ? timestamp

  /** DustLog */
  val dustLog: BinanceTask[BinanceUri] =
    "sapi" \ v1 \ asset \ dribblet ? timestamp

  /** Daily Account Snapshot */
  val dailyAccountSnapshot: BinanceTask[BinanceUri] =
    ("sapi" \ v1 / "accountSnapshot") ? (timestamp & _type("SPOT"))

  override def run: IO[Unit] =
    for {
      uri1 <- withdrawHistory(client)
      uri2 <- depositAddress(client)
      uri3 <- accountAPITradingStatus(client)
      uri4 <- dustLog(client)
      uri5 <- dailyAccountSnapshot(client)
      _ <- IO.println(uri1.show())
      _ <- IO.println(uri2.show())
      _ <- IO.println(uri3.show())
      _ <- IO.println(uri4.show())
      _ <- IO.println(uri5.show())

    } yield ()
}
