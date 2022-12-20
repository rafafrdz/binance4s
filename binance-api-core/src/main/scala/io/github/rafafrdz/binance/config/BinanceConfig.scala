package io.github.rafafrdz.binance.config

import cats.effect.IO
import io.github.rafafrdz.binance.config.credential.OAuth
import io.github.rafafrdz.binance.config.mode.{BinanceMode, Test}

trait BinanceConfig {

  def credential: IO[OAuth]

  def mode: IO[BinanceMode]

}

object BinanceConfig {

  def default: BinanceConfig = new BinanceConfig {

    override def credential: IO[OAuth] = IO.pure(OAuth.default)

    override def mode: IO[BinanceMode] = IO.pure(Test)
  }
}