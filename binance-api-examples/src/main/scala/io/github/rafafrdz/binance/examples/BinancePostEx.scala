package io.github.rafafrdz.binance.examples

import cats.effect._
import io.circe.Json
import io.github.rafafrdz.binance.BinanceTask
import io.github.rafafrdz.binance.api.function.query._
import io.github.rafafrdz.binance.api.function.uri._
import io.github.rafafrdz.binance.api.implicits._
import io.github.rafafrdz.binance.api.query.BinanceQuery
import io.github.rafafrdz.binance.api.uri.BinanceURI
import io.github.rafafrdz.binance.client.BinanceClient
import org.http4s.circe.CirceEntityCodec._


object BinancePostEx extends IOApp.Simple {

  lazy val bnc: BinanceClient =
    BinanceClient
      .build
      .test
      .credential
      .create()

  val queryPost: BinanceTask[BinanceQuery] =
    (symbol("LTCBTC") & side("BUY") & _type("LIMIT") &
      timeInForce("GTC") & quantity(1) & price(0.1) &
      recvWindow(5000) & timestamp).?

  //  val bPostUri: BinanceTask[BinanceUri] = api \ v3 \ order ? queryPost

  /** Disable Fast Withdraw Switch */
  val disableFastWithdrawSwitchTask: BinanceTask[BinanceURI] =
    mode("api") \ sapi \ v1 \ account \ disableFastWithdrawSwitch ? timestamp

  override def run: IO[Unit] =
    for {
      json <- bnc.post[Json](disableFastWithdrawSwitchTask)
      _ <- IO.println(json)
    } yield ()
}
