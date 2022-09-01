package dev.rafafrdz.binance.core.session

import dev.rafafrdz.binance.api.implicits.types._
import dev.rafafrdz.binance.core.session.security.Credential
import Credential.NonProvided
import dev.rafafrdz.binance.core.config.{BinanceConfig, TypeCheck}
import dev.rafafrdz.binance.core.session.options.BinanceAPIUri

trait BinanceContext {

  protected val bconf: BinanceConfig

  def get[T: TypeCheck](keys: String*): Option[T] = bconf.get[T](keys:_*)

  def credential(): Credential =
    bconf.get[Credential](Credential.refOption).getOrElse(NonProvided)

  def api(): BinanceAPIUri =
    bconf.getOrElse[BinanceAPIUri](BinanceAPIUri.refOption)(BinanceAPIUri.API)
}

object BinanceContext {
  def build(): BinanceContextBuilder = new BinanceContextBuilder {}

}

