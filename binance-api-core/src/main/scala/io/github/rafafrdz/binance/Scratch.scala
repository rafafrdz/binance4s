package io.github.rafafrdz.binance

import cats.effect.IO
import cats.effect.unsafe.implicits.global
import io.github.rafafrdz.binance.api.query.BinanceQuery
import io.github.rafafrdz.binance.client.BinanceClient
import io.github.rafafrdz.binance.security.Hash

object Scratch extends App {

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
      .startTime("20/08/2022 17:15:00")
      .endTime("2022/08/24")
      .symbol("BNBUSDT")
      .timestamp.?

  val algo2 = bclient.executeUnsafe(query).show()
  0
}
