package io.github.rafafrdz.binance.client

import cats.effect.IO
import com.typesafe.config.Config
import io.github.rafafrdz.binance.config._
import io.github.rafafrdz.binance.config.credential.{BinanceCredential, OAuth}
import io.github.rafafrdz.binance.config.mode.{API, BinanceMode, Test}
import io.github.rafafrdz.binance.config.option.{BinanceOption, BinanceOptionT}

trait BinanceClientBuilder {

  self =>

  val bconf: BinanceConfig

  /** Builder */
  def create(): BinanceClient = new BinanceClient {
    override val config: BinanceConfig = bconf
  }

  def from(conf: BinanceConfig): BinanceClientBuilder = new BinanceClientBuilder {
    override val bconf: BinanceConfig = conf
  }

  def from(conf: Config): BinanceClientBuilder = from(BinanceConfig.from(conf))

  def from(path: String): BinanceClientBuilder = from(BinanceConfig.from(path))

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
    for {
      ioa <- algebra.parse(ref)
      iob = ioa.getOrElse(default)
    } yield iob
  }
}
