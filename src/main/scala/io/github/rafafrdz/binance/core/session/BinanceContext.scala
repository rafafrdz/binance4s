package io.github.rafafrdz.binance.core.session

import io.github.rafafrdz.binance.api.implicits.types._
import io.github.rafafrdz.binance.core.config.{BinanceConfig, TypeCheck}
import io.github.rafafrdz.binance.core.session.options.BinanceAPIUri
import io.github.rafafrdz.binance.core.session.security.Credential
import io.github.rafafrdz.binance.core.session.security.Credential.NonProvided

trait BinanceContext {

  protected val bconf: BinanceConfig

  def get[T: TypeCheck](keys: String*): Option[T] = bconf.get[T](keys: _*)

  def credential(): Credential =
    bconf.get[Credential](Credential.refOption).getOrElse(NonProvided)

  def api(): BinanceAPIUri =
    bconf.getOrElse[BinanceAPIUri](BinanceAPIUri.refOption)(BinanceAPIUri.API)
}

object BinanceContext {
  def build(): BinanceContextBuilder = new BinanceContextBuilder {}

}

