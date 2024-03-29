package io.github.rafafrdz.binance.client

import cats.effect.{IO, unsafe}
import io.github.rafafrdz.binance.BinanceTask
import io.github.rafafrdz.binance.config.BinanceConfig


trait BinanceClient {
  self =>

  val config: BinanceConfig

  def run[T](task: BinanceTask[T]): IO[T] = task(self)

  def runUnsafe[T](task: BinanceTask[T])(implicit runtime: unsafe.IORuntime): T =
    run(task).unsafeRunSync()
}

object BinanceClient {
  def build: BinanceClientBuilder = new BinanceClientBuilder {
    override val bconf: BinanceConfig = BinanceConfig.default
  }
}

