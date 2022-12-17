package io.github.rafafrdz.binance2

import cats.effect.IO
import cats.effect.unsafe.implicits.global
import io.github.rafafrdz.binance2.api.query.BinanceQuery
import io.github.rafafrdz.binance2.client.BinanceClient
import io.github.rafafrdz.binance2.security.Hash

object Scratch extends App {

  val bclient: BinanceClient =
    BinanceClient
      .create()
      .credential("user", "pass")
      .test
      .build()

  val taskHash: BinanceTask[String] = Hash.hmec256("strange")

  val algo: IO[String] = bclient.execute(taskHash)

  val query: BinanceTask[BinanceQuery] =
    BinanceQuery
      .build()
      .startTime("20/08/2022 17:15:00")
      .endTime("2022/08/24")
      .symbol("BNBUSDT")
      .timestamp()
      .formalize()

  val algo2 = bclient.executeUnsafe(query).show()
  0
}