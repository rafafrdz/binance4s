package io.github.rafafrdz.binance.api.request

import cats.effect._
import io.circe.Json
import io.github.rafafrdz.binance.BinanceTask
import io.github.rafafrdz.binance.api.function.query._
import io.github.rafafrdz.binance.api.function.uri._
import io.github.rafafrdz.binance.api.uri.BinanceUri
import io.github.rafafrdz.binance.client.BinanceClient
import org.http4s.circe.CirceEntityCodec._

object GetScratch extends IOApp.Simple with BinanceExecute {

  lazy val bclient: BinanceClient =
    BinanceClient.build.create()

  /** GET */
  val ping: BinanceTask[BinanceUri] = ("api" / "v3" / "ping").?

  /** Check Server Time */
  val checkServerTime: BinanceTask[BinanceUri] = ("api" / "v3" / "time").mode("api").?

  /** All Coins' Information */
  lazy val allCoinsInformation: BinanceTask[BinanceUri] = (sapi \ v1 \ capital \ config \ getall).?

  /** Deposit History */
  lazy val depositHistory: BinanceTask[BinanceUri] = sapi \ v1 \ capital \ deposit \ hisrec ? (timestamp & startTime("01/06/2022"))

  /** System Status */
  lazy val systemStatus: BinanceTask[BinanceUri] = sapi \ v1 \ system \ statuss ? timestamp


  override def run: IO[Unit] =
    for {
      pingUri <- bclient.run(ping)
      respPing <- bclient.run(execute[String](ping))
      respCheck <- bclient.run(execute[String](checkServerTime))
      _ <- IO.println(pingUri.show())
      _ <- IO.println(respPing)
      _ <- IO.println(respCheck)
      dailyAccountSnapshotUri <- bclient.run(allCoinsInformation)
      _ <- IO.println(dailyAccountSnapshotUri.show())
      respDaily <- bclient.run(execute[Json](depositHistory))
      _ <- IO.println(respDaily)
    } yield ()
}
