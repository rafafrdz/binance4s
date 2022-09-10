package io.github.rafafrdz.binance.core.session

import io.github.rafafrdz.binance.core.config.BinanceConfig
import io.github.rafafrdz.binance.core.session.BinanceContextBuilder.add
import io.github.rafafrdz.binance.core.session.options.BinanceAPIUri
import io.github.rafafrdz.binance.core.session.options.BinanceAPIUri.Test

trait BinanceContextBuilder {
  self =>
  protected val options: Map[String, String] = Map.empty

  protected val bconf_ : BinanceConfig = BinanceConfig.empty

  def create(): BinanceContext =
    BinanceContextBuilder.create(self)

  def api(mode: String): BinanceContextBuilder =
    add(self)(s"${BinanceConfig.BINANCE}.${BinanceAPIUri.refOption}" -> mode)

  /** Esto podria ser para variar entre la api de produccion con la de testing */
  def testing(): Unit = api(Test.value)

  def from(path: String): BinanceContextBuilder =
    from(BinanceConfig.from(path).define())

  def from(bconf: BinanceConfig): BinanceContextBuilder =
    BinanceContextBuilder.from(self, bconf)

  def option(key: String, value: String): BinanceContextBuilder =
    add(self)(key -> value)

  def default(): BinanceContextBuilder =
    from("config/application")

}

private[core] object BinanceContextBuilder {
  def create(bctx: BinanceContextBuilder): BinanceContext = new BinanceContext {
    override protected val bconf: BinanceConfig = bctx.bconf_.set(bctx.options).define()
  }

  def add(bctx: BinanceContextBuilder)(newOpts: (String, String)*): BinanceContextBuilder = new BinanceContextBuilder {
    override val options: Map[String, String] = bctx.options ++ newOpts.toMap
  }

  def from(bctx: BinanceContextBuilder, bconf: BinanceConfig): BinanceContextBuilder = new BinanceContextBuilder {
    override protected val bconf_ : BinanceConfig = bctx.bconf_.merge(bconf)
  }
}
