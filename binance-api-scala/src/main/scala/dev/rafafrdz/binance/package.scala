package dev.rafafrdz

import dev.rafafrdz.binance.api.options.QueryRequest
import dev.rafafrdz.binance.core.session.BinanceContext
import requests.Response

package object binance {
  type BinanceTask[S] = BinanceContext => S
  type BinanceOptionTask[S] = BinanceTask[Option[S]]
  type BinanceQuery = BinanceOptionTask[QueryRequest]
  type BinanceRequest = BinanceOptionTask[Response]

}
