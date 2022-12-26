package io.github.rafafrdz.binance.api.uri

import io.github.rafafrdz.binance.BinanceTask
import io.github.rafafrdz.binance.api.query.BinanceQuery
import io.github.rafafrdz.binance.config.mode._

case class BinanceURI private[api](mode: BinanceMode = Test, path: Vector[String] = Vector.empty, query: BinanceQuery = BinanceQuery.build) {
  self =>

  private lazy val uri: String = mode.uri

  def mode(api: BinanceMode): BinanceURI = self.copy(mode = api)

  def mode(api: String): BinanceURI = self.copy(mode = BinanceMode.cast(api))

  def from: BinanceTask[BinanceURI] = client => client.config.mode.map(mode(_))

  def /(path: String): BinanceURI = self.copy(path = self.path :+ path)

  def \(uri: BinanceURI): BinanceURI = self.copy(path = self.path ++ uri.path)

  private def ??(query: BinanceQuery): BinanceURI = self.copy(query = query)

  def ? : BinanceTask[BinanceURI] = ?(query.?)

  def ?(query: BinanceQuery): BinanceTask[BinanceURI] = ?(query.?)

  def ?(query: BinanceTask[BinanceQuery]): BinanceTask[BinanceURI] =
    client => query(client).map(??)

  def api: BinanceURI = /("api")

  def sapi: BinanceURI = /("sapi")

  def account: BinanceURI = /("account")

  def order: BinanceURI = /("order")

  def disableFastWithdrawSwitch: BinanceURI = /("disableFastWithdrawSwitch")

  def enableFastWithdrawSwitch: BinanceURI = /("enableFastWithdrawSwitch")

  def capital: BinanceURI = /("capital")

  def config: BinanceURI = /("config")

  def getall: BinanceURI = /("getall")

  def withdraw: BinanceURI = /("withdraw")

  def deposit: BinanceURI = /("deposit")

  def addrezz: BinanceURI = /("address")

  def hisrec: BinanceURI = /("hisrec")

  def history: BinanceURI = /("history")

  def asset: BinanceURI = /("asset")

  def dribblet: BinanceURI = /("dribblet")

  def apiTradingStatus: BinanceURI = /("apiTradingStatus")

  def time: BinanceURI = /("time")

  def accountSnapshot: BinanceURI = /("accountSnapshot")

  def system: BinanceURI = /("system")

  def statuss: BinanceURI = /("status")

  def _apply: BinanceURI = /("apply")

  def v(n: Int): BinanceURI = /(s"v$n")

  def v1: BinanceURI = v(1)

  def v2: BinanceURI = v(2)

  def v3: BinanceURI = v(3)

  def v4: BinanceURI = v(4)

  def v5: BinanceURI = v(5)

  def v6: BinanceURI = v(6)

  def show(): String = uri + "/" + path.mkString("/") + '?' + query.show()
}

object BinanceURI {
  def build: BinanceURI = BinanceURI()
}
