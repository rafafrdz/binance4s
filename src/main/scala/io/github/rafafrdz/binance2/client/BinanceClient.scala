package io.github.rafafrdz.binance2.client

import cats.effect.{IO, unsafe}
import io.github.rafafrdz.binance2.BinanceTask
import io.github.rafafrdz.binance2.config.BinanceConfig


trait BinanceClient {
  self =>

  val config: BinanceConfig

  def execute[T](task: BinanceTask[T]): IO[T] = task(self)

  def executeUnsafe[T](task: BinanceTask[T])(implicit runtime: unsafe.IORuntime): T =
    execute(task).unsafeRunSync()
}

object BinanceClient {
  def create(): BinanceClientBuilder = new BinanceClientBuilder {
    override val bconf: BinanceConfig = BinanceConfig.default
  }
}

