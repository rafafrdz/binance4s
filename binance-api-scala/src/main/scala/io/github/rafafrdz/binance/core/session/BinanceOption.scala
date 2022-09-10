package io.github.rafafrdz.binance.core.session

private[session] trait BinanceOption extends Product with Serializable {
  /** Referential name of the option */
  private[session] val refOption: String
}