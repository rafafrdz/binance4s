package io.github.rafafrdz.binance.api.uri

import io.github.rafafrdz.binance.BinanceTask
import io.github.rafafrdz.binance.api.query.BinanceQuery
import io.github.rafafrdz.binance.config.mode.{BinanceMode, Test}

case class BinanceUri private[api](mode: BinanceMode = Test, path: Vector[String] = Vector.empty, query: BinanceQuery = BinanceQuery.build) {
  self =>

  private lazy val uri: String = mode.uri

  def mode(api: BinanceMode): BinanceUri = self.copy(mode = api)

  def from: BinanceTask[BinanceUri] = client => client.config.mode.map(mode(_))

  def /(path: String): BinanceUri = self.copy(path = self.path :+ path)

  def \(uri: BinanceUri): BinanceUri = self.copy(path = self.path ++ uri.path)

  private def ??(query: BinanceQuery): BinanceUri = self.copy(query = query)

  def ?(query: BinanceQuery): BinanceTask[BinanceUri] = ?(query.?)

  def ?(query: BinanceTask[BinanceQuery]): BinanceTask[BinanceUri] =
    client => query(client).map(??)

  def api: BinanceUri = /("api")

  def sapi: BinanceUri = /("sapi")

  def account: BinanceUri = /("account")

  def disableFastWithdrawSwitch: BinanceUri = /("disableFastWithdrawSwitch")

  def enableFastWithdrawSwitch: BinanceUri = /("enableFastWithdrawSwitch")

  def capital: BinanceUri = /("capital")

  def withdraw: BinanceUri = /("withdraw")

  def deposit: BinanceUri = /("deposit")

  def addrezz: BinanceUri = /("address")

  def hisrec: BinanceUri = /("hisrec")

  def history: BinanceUri = /("history")
  def asset: BinanceUri = /("asset")
  def dribblet: BinanceUri = /("dribblet")
  def apiTradingStatus: BinanceUri = /("apiTradingStatus")
  def accountSnapshot: BinanceUri = /("accountSnapshot")

  def _apply: BinanceUri = /("apply")

  def v(n: Int): BinanceUri = /(s"v$n")

  def v1: BinanceUri = v(1)

  def v2: BinanceUri = v(2)

  def v3: BinanceUri = v(3)

  def v4: BinanceUri = v(4)

  def v5: BinanceUri = v(5)

  def v6: BinanceUri = v(6)

  def show(): String = uri + "/" + path.mkString("/") + '?' + query.show()
}

object BinanceUri {
  def build: BinanceUri = BinanceUri()
}
