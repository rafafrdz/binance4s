package io.github.rafafrdz.binance.config.mode

import io.github.rafafrdz.binance.config.option.{BinanceOption, BinanceOptionT}

sealed trait BinanceMode extends BinanceOption {

  val ref: String = "mode"

  val uri: String

  val value: String
}

object BinanceMode {

  val ref: String = "binance.api.mode"

  type API = API.type

  type Test = Test.type

  def default: BinanceMode = Test

  def cast(mode: String): BinanceMode =
    if (mode == API.value) API else Test

  implicit val apiAlgebra: BinanceOptionT[API] =
    BinanceOptionT.make[API](_ => API, opt => Map(ref -> opt.value))

  implicit val testAlgebra: BinanceOptionT[Test] =
    BinanceOptionT.make[Test](_ => Test, opt => Map(ref -> opt.value))

}

case object API extends BinanceMode {

  val uri: String = "https://api.binance.com"

  val value: String = "api"

  override def toString: String = value

}

case object Test extends BinanceMode {

  val uri: String = "https://testnet.binance.vision/api/v3/account?"

  val value: String = "test"

  override def toString: String = value
}
