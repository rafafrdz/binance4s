package dev.rafafrdz.binance.core.config

import com.typesafe.config.{Config, ConfigFactory, ConfigValueFactory}

import scala.util.Try

trait BinanceConfigBuilder extends BinanceConfig {
  self =>

  def define(): BinanceConfig = BinanceConfigBuilder.define(self)

}

private[core] object BinanceConfigBuilder {

  val empty: BinanceConfigBuilder = new BinanceConfigBuilder {}

  def set[B <: BinanceConfig](bconf: B)(options: Map[String, String]): BinanceConfigBuilder = {
    lazy val optionsConf = options.map { case (key, value) => (conf: Config) => conf.withValue(key, ConfigValueFactory.fromAnyRef(value)) }
    new BinanceConfigBuilder {
      override val conf: Option[Config] = bconf.conf match {
        case Some(value) => Option(optionsConf.foldLeft(value) { case (conf, confF) => confF(conf) })
        case None => Option(optionsConf.foldLeft(ConfigFactory.load()) { case (conf, confF) => confF(conf) })
      }
    }
  }

  def set[B <: BinanceConfig](bconf: B, options: (String, String)*): BinanceConfigBuilder =
    set(bconf)(options.toMap)

  def define(bcbuilder: BinanceConfigBuilder): BinanceConfig =
    new BinanceConfig {
      override val conf: Option[Config] = bcbuilder.conf match {
        case Some(value) => Option(Try(value.getConfig(BinanceConfig.BINANCE)).getOrElse(value))
        case None => None
      }
    }
}
