package io.github.rafafrdz.binance.examples

import cats.effect.{IO, IOApp}
import io.github.rafafrdz.binance.BinanceTask
import io.github.rafafrdz.binance.api.function.query._
import io.github.rafafrdz.binance.api.function.uri._
import io.github.rafafrdz.binance.api.uri.BinanceURI
import io.github.rafafrdz.binance.client.BinanceClient

object BinanceURIEx extends IOApp.Simple {

  lazy val bnc: BinanceClient = BinanceClient.build.create()

  /** Withdraw History */
  val withdrawHistory: BinanceTask[BinanceURI] =
    sapi \ v1 \ capital \ history ?
      (
        coin("USDT")
          & withdrawOrderId("WITHDRAWtest123")
          & status(6) & limit & startTime
          & endTime & timestamp
        )

  /** Deposit Address */
  val depositAddress: BinanceTask[BinanceURI] =
    sapi \ v1 \ capital \ deposit \ addrezz ? (coin("BTC") & timestamp)

  /** Account API Trading Status */
  val accountAPITradingStatus: BinanceTask[BinanceURI] =
    ("sapi" / "v1" / "account" / "apiTradingStatus") ? timestamp

  /** DustLog */
  val dustLog: BinanceTask[BinanceURI] =
    "sapi" \ v1 \ asset \ dribblet ? timestamp

  /** Daily Account Snapshot */
  val dailyAccountSnapshot: BinanceTask[BinanceURI] =
    ("sapi" \ v1 / "accountSnapshot") ? (timestamp & _type("SPOT"))

  override def run: IO[Unit] =
    for {
      uri1 <- bnc.run(withdrawHistory)
      uri2 <- bnc.run(depositAddress)
      uri3 <- bnc.run(accountAPITradingStatus)
      uri4 <- bnc.run(dustLog)
      uri5 <- bnc.run(dailyAccountSnapshot)
      _ <- IO.println(uri1.show())
      _ <- IO.println(uri2.show())
      _ <- IO.println(uri3.show())
      _ <- IO.println(uri4.show())
      _ <- IO.println(uri5.show())

    } yield ()
}
