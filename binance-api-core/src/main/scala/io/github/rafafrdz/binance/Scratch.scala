package io.github.rafafrdz.binance

import cats.effect.{IO, IOApp}
import cats.effect.unsafe.implicits.global
import io.github.rafafrdz.binance.api.query.BinanceQuery
import io.github.rafafrdz.binance.client.BinanceClient
import io.github.rafafrdz.binance.security.Hash

object Scratch extends IOApp.Simple {

  val bclient: BinanceClient =
    BinanceClient
      .build
      .credential
      .test
      .create()

  val taskHash: BinanceTask[String] = Hash.hmec256("strange")

  val algo: IO[String] = bclient.execute(taskHash)

  val query: BinanceTask[BinanceQuery] =
    BinanceQuery
      .build
      .symbol("BNBUSDT")
      .startTime("20/08/2022 17:15:00")
      .endTime
      .timestamp
      .?

  val algo2 = bclient.executeUnsafe(query).show()
  0

  override def run: IO[Unit] = for {
    q <- bclient.execute(query)
  } yield println(q.show())
}
