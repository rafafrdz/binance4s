package dev.rafafrdz.binance.session

import dev.rafafrdz.binance.config.BinanceConfig
import dev.rafafrdz.binance.session.BinanceContextBuilder.add
import dev.rafafrdz.binance.session.options.BinanceAPIUri
import dev.rafafrdz.binance.session.options.BinanceAPIUri.TestAPI
import dev.rafafrdz.binance.session.security.Credential
import dev.rafafrdz.binance.session.security.Credential.NonProvided

import scala.collection.mutable

private[session] trait BinanceContextBuilder {
  self =>
  protected val options: mutable.Map[String, BinanceOption] = mutable.Map.empty

  def setCredentials(crt: Credential): BinanceContextBuilder = BinanceContextBuilder.setCredentials(self, crt)

  def create(): BinanceContext = BinanceContextBuilder.create(self)

  //    def fromConfig(bcfg: BinanceConfig): BinanceContextBuilder

  /** Esto podria ser para variar entre la api de produccion con la de testing */
  def testing(): Unit = api(TestAPI)

  def option(key: String, value: BinanceOption): BinanceContextBuilder = add(self)(key -> value)

  def api(mode: BinanceAPIUri): BinanceContextBuilder = add(self)(mode.refOption -> mode)

  def api(mode: String): BinanceContextBuilder = api(BinanceAPIUri.get(mode))

  def from(path: String): BinanceContextBuilder =
    setCredentials(Credential.from(BinanceConfig.getFrom(path)))

  def default(): BinanceContextBuilder =
    from("config/application")

}

private[session] object BinanceContextBuilder {
  def create(bctx: BinanceContextBuilder): BinanceContext = new BinanceContext {
    override val credential: Credential =
      bctx.options.get(Credential.refOption) match {
        case Some(crt: Credential) => crt
        case None => NonProvided
      }
    override protected val bconf: BinanceConfig = BinanceConfig.getFrom("config/application")
//    todo. override protected val bconf: BinanceConfig = BinanceConfig.getFrom(bctx.options)
  }

  def setCredentials(bctx: BinanceContextBuilder, crt: Credential): BinanceContextBuilder = add(bctx)(crt.refOption -> crt)

  def add(bctx: BinanceContextBuilder)(newOpts: (String, BinanceOption)*): BinanceContextBuilder = new BinanceContextBuilder {
    override val options: mutable.Map[String, BinanceOption] = bctx.options.addAll(newOpts)
  }

  //  def fromConfig(bcfg: BinanceConfig): BinanceContextBuilder = ???
}
