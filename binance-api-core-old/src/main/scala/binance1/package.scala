import io.github.rafafrdz.binance1.api.options.QueryRequest
import io.github.rafafrdz.binance1.core.session.BinanceContext
import requests.Response

package object binance1 {
  type BinanceTask[S] = BinanceContext => S
  type BinanceOptionTask[S] = BinanceTask[Option[S]]
  type BinanceQuery = BinanceOptionTask[QueryRequest]
  type BinanceRequest = BinanceOptionTask[Response]

}
