package io.github.rafafrdz.binance2.client

import cats.effect.IO
import io.github.rafafrdz.binance2.config._
import io.github.rafafrdz.binance2.config.credential.{BinanceCredential, OAuth}
import io.github.rafafrdz.binance2.config.mode.{API, BinanceMode, Test}
import io.github.rafafrdz.binance2.config.option.{BinanceOption, BinanceOptionT}

trait BinanceClientBuilder {

  self =>

  val bconf: BinanceConfig

  /** Builder */
  def build(): BinanceClient = new BinanceClient {
    override val config: BinanceConfig = bconf
  }

  /** *************
   * Setter methods
   * ************** */

  /** Credentials */
  def credential(accessKey: String, secretKey: String): BinanceClientBuilder =
    credential(OAuth(accessKey, secretKey))

  def credential(oauth: OAuth): BinanceClientBuilder =
    setCredential(IO.pure(oauth))

  def credential(implicit algebra: BinanceOptionT[OAuth]): BinanceClientBuilder = {
    val newMode: IO[OAuth] = BinanceClientBuilder.parse(BinanceCredential.ref, OAuth.default)
    setCredential(newMode)
  }

  /** private set credential */
  private def setCredential(newCred: IO[OAuth]): BinanceClientBuilder = {

    val newBConf: BinanceConfig =
      new BinanceConfig {

        override def credential: IO[OAuth] = newCred

        override def mode: IO[BinanceMode] = bconf.mode
      }

    BinanceClientBuilder.from(newBConf)
  }

  /** Mode */
  def mode(modeType: String): BinanceClientBuilder =
    mode(BinanceMode.cast(modeType))

  def mode(modeType: BinanceMode): BinanceClientBuilder =
    setMode(IO.pure(modeType))

  def api: BinanceClientBuilder = mode(API.value)

  def test: BinanceClientBuilder = mode(Test.value)

  private def setMode(implicit algebra: BinanceOptionT[BinanceMode]): BinanceClientBuilder = {
    val newMode: IO[BinanceMode] = BinanceClientBuilder.parse(BinanceMode.ref, BinanceMode.default)
    setMode(newMode)
  }

  /** private set mode */
  private def setMode(newMode: IO[BinanceMode]): BinanceClientBuilder = {
    val newBConf: BinanceConfig =
      new BinanceConfig {

        override def credential: IO[OAuth] = bconf.credential

        override def mode: IO[BinanceMode] = newMode
      }

    BinanceClientBuilder.from(newBConf)
  }


}

object BinanceClientBuilder {
  def from(binanceConfig: BinanceConfig): BinanceClientBuilder =
    new BinanceClientBuilder {
      val bconf: BinanceConfig = binanceConfig
    }

  /** Private methods */
  private def parse[O <: BinanceOption](ref: String, default: O)(implicit algebra: BinanceOptionT[O]): IO[O] = {
    algebra.parse(ref).map(opt => opt.getOrElse(default))
  }
}
