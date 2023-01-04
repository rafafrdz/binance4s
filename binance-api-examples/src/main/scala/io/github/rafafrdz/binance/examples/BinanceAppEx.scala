package io.github.rafafrdz.binance.examples


import cats.effect.{ExitCode, IO, IOApp}
import io.github.rafafrdz.binance.BinanceTask
import io.github.rafafrdz.binance.api.function.query._
import io.github.rafafrdz.binance.api.function.uri._
import io.github.rafafrdz.binance.api.implicits._
import io.github.rafafrdz.binance.api.query.BinanceQuery
import io.github.rafafrdz.binance.api.uri.BinanceURI
import io.github.rafafrdz.binance.client.BinanceClient

object BinanceAppEx extends IOApp {

  /** Binance Client */
  val bnc: BinanceClient =
    BinanceClient
      .build
      .test
      .credential
      .create()

  /** Binance Query */
  val query: BinanceTask[BinanceQuery] =
    timestamp.?

  /** Binance URI */
  val uri: BinanceTask[BinanceURI] =
    sapi \ v1 \ system \ statuz ? query


  /** Response */
  val response: IO[String] = bnc.get[String](uri)

  override def run(args: List[String]): IO[ExitCode] =
    for {
      resp <- response
      _ <- IO.println(resp)
    } yield ExitCode.Success

}
