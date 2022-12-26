package io.github.rafafrdz.binance.api.request

import cats.effect._
import io.github.rafafrdz.binance.BinanceTask
import io.github.rafafrdz.binance.api.function.query._
import io.github.rafafrdz.binance.api.function.uri._
import io.github.rafafrdz.binance.api.query.BinanceQuery
import io.github.rafafrdz.binance.api.uri.BinanceUri
import io.github.rafafrdz.binance.client.BinanceClient
import org.http4s.dsl.io.POST


object PostScratch extends IOApp.Simple with BinanceExecute {

  lazy val client: BinanceClient =
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
  val disableFastWithdrawSwitchTask: BinanceTask[BinanceUri] =
    mode("api") \ sapi \ v1 \ account \ disableFastWithdrawSwitch ? timestamp

  override def run: IO[Unit] =
    for {
      json <- client.run(execute[String](POST, disableFastWithdrawSwitchTask))
      _ <- IO.println(json)
    } yield ()
}
