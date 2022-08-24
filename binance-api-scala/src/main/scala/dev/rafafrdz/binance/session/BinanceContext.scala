package dev.rafafrdz.binance.session

import dev.rafafrdz.binance.api.implicits.types._
import dev.rafafrdz.binance.config.BinanceConfig
import dev.rafafrdz.binance.session.options.BinanceAPIUri
import dev.rafafrdz.binance.session.security.Credential

trait BinanceContext {

  protected val bconf: BinanceConfig

  def credential(): Credential = {
    val acc: Option[String] = bconf.get[String]("credential", "access", "key")
    val secr: Option[String] = bconf.get[String]("credential", "secret", "key")
    Credential.create(acc, secr)
  }

  def api(): BinanceAPIUri =
    bconf.getOrElse[BinanceAPIUri]("api.mode")(BinanceAPIUri.API)
}

object BinanceContext {
  def build(): BinanceContextBuilder = new BinanceContextBuilder {}

}

