package io.github.rafafrdz

import io.github.rafafrdz.binance.api.options.QueryRequest
import io.github.rafafrdz.binance.core.session.BinanceContext
import requests.Response

package object binance {
  type BinanceTask[S] = BinanceContext => S
  type BinanceOptionTask[S] = BinanceTask[Option[S]]
  type BinanceQuery = BinanceOptionTask[QueryRequest]
  type BinanceRequest = BinanceOptionTask[Response]

}
