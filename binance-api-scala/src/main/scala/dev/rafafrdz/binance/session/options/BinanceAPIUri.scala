package dev.rafafrdz.binance.session.options

import dev.rafafrdz.binance.api.constants._
import dev.rafafrdz.binance.session.BinanceOption


sealed trait BinanceAPIUri extends BinanceOption {
  /** Referential name of the option */
  override private[session] val refOption = "api.uri"

  override def toString: String = uri

  val uri: String
}

object BinanceAPIUri {
  def get: String => BinanceAPIUri = mode =>
    mode.toLowerCase() match {
      case "api" => API
      case "test" => TestAPI
    }

  case object API extends BinanceAPIUri {
    override val uri: String = BINANCE_API_URI
  }

  case object TestAPI extends BinanceAPIUri {
    override val uri: String = BINANCE_API_TEST_URI
  }
}


