package dev.rafafrdz.binance.api.reqest

import dev.rafafrdz.binance.BinanceOptionTask
import dev.rafafrdz.binance.api.constants.BINANCE_API_URI
import dev.rafafrdz.binance.api.options.QueryRequest
import requests.Response

import scala.util.Try

object get {

  /** /api/v3/account? */
  def accountInformation(query: BinanceOptionTask[QueryRequest]): BinanceOptionTask[Response] =
    bctx =>
      for {
        qr <- query(bctx)
        url <- Option(s"${bctx.api().uri}/api/v3/account?$qr")
        acc <- bctx.credential().getAccessKey
        req <- Try(requests.get(url, headers = Map("Content-Type" -> "application/json", "X-MBX-APIKEY" -> acc))).toOption
      } yield req

  /** /sapi/v1/capital/deposit/hisrec? */
  def depositHistory(query: BinanceOptionTask[QueryRequest]): BinanceOptionTask[Response] =
    bctx =>
      for {
        qr <- query(bctx)
        url <- Option(s"${bctx.api().uri}/sapi/v1/capital/deposit/hisrec?$qr")
        acc <- bctx.credential().getAccessKey
        req <- Try(requests.get(url, headers = Map("Content-Type" -> "application/json", "X-MBX-APIKEY" -> acc))).toOption
      } yield req

  /** /api/v3/avgPrice? */

  def currentAVGPrice(query: BinanceOptionTask[QueryRequest]): BinanceOptionTask[Response] =
    bctx =>
      for {
        qr <- query(bctx)
        url <- Option(s"${bctx.api().uri}/api/v3/avgPrice?$qr")
        acc <- bctx.credential().getAccessKey
        req <- Try(requests.get(url, headers = Map("Content-Type" -> "application/json", "X-MBX-APIKEY" -> acc))).toOption
      } yield req

  /** /sapi/v1/convert/tradeFlow? */
  def convertTradeHistory(query: BinanceOptionTask[QueryRequest]): BinanceOptionTask[Response] =
    bctx =>
      for {
        qr <- query(bctx)
        url <- Option(s"${bctx.api().uri}/sapi/v1/convert/tradeFlow?$qr")
        acc <- bctx.credential().getAccessKey
        req <- Try(requests.get(url, headers = Map("Content-Type" -> "application/json", "X-MBX-APIKEY" -> acc))).toOption
      } yield req
}
