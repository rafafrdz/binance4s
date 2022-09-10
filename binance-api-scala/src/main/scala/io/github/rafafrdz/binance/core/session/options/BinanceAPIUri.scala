package io.github.rafafrdz.binance.core.session.options

import io.github.rafafrdz.binance.api.constants._
import io.github.rafafrdz.binance.core.session.BinanceOption


sealed trait BinanceAPIUri extends BinanceOption {
  /** Referential name of the option */
  override private[session] val refOption = BinanceAPIUri.refOption

  override def toString: String = uri

  val uri: String
  val value: String
}

object BinanceAPIUri {
  private[session] val refOption = "api.mode"

  def get: String => BinanceAPIUri = mode =>
    mode.toLowerCase() match {
      case API.value => API
      case Test.value => Test
    }

  case object API extends BinanceAPIUri {
    val value: String = "api"
    val uri: String = BINANCE_API_URI
  }

  case object Test extends BinanceAPIUri {
    val value: String = "test"
    val uri: String = BINANCE_API_TEST_URI
  }
}


