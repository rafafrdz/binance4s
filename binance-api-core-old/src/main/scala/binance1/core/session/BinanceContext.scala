package binance1.core.session

import io.github.rafafrdz.binance1.api.implicits.types._
import io.github.rafafrdz.binance1.core.session.security.Credential.NonProvided
import io.github.rafafrdz.binance1.core.config.{BinanceConfig, TypeCheck}
import io.github.rafafrdz.binance1.core.session.options.BinanceAPIUri
import io.github.rafafrdz.binance1.core.session.security.Credential

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

