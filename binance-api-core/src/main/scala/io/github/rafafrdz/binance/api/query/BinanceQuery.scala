package io.github.rafafrdz.binance.api.query

import cats.effect.IO
import cats.implicits._
import io.github.rafafrdz.binance.BinanceTask
import io.github.rafafrdz.binance.api.utils
import io.github.rafafrdz.binance.security.Hash


case class BinanceQuery private[api](options: Set[QueryOption[String]] = Set.empty, time: Option[Long] = None) {
  self =>

  def keys: Set[String] = options.map(_.key)

  def add[T](kv: (String, T)*): BinanceQuery = BinanceQuery.set(self, kv: _*)

  def add(ops: Set[QueryOption[String]]): BinanceQuery = BinanceQuery.set(self, ops)

  def merge(other: BinanceQuery*): BinanceQuery = BinanceQuery.merge(self +: other: _*)

  def &(other: BinanceQuery): BinanceQuery = merge(other)

  def &(other: QueryOption[String]): BinanceQuery = add(Set(other))

  /** Methods for specifics options in the query */

  def timestamp: BinanceQuery = {
    lazy val t: Long = System.currentTimeMillis()
    add("timestamp" -> t).copy(time = Option(t))
  }

  /** Default: 90 days from current timestamp */
  def startTime: BinanceQuery = add("startTime" -> utils.timestamp.before(90).getTime)

  /** Default: 90 days from current timestamp */
  def startTime(time: Long): BinanceQuery = add("startTime" -> time)

  /** Default: 90 days from current timestamp */
  def startTime(timestamp: String): BinanceQuery = startTime(utils.timestamp.parse(timestamp).getTime)

  /** Default: present timestamp */
  def endTime(time: Long): BinanceQuery = add("endTime" -> time)

  /** Default: present timestamp */
  def endTime: BinanceQuery = endTime(utils.timestamp.current.getTime)

  /** Default: present timestamp */
  def endTime(timestamp: String): BinanceQuery = endTime(utils.timestamp.parse(timestamp).getTime)

  def symbol(currency: String): BinanceQuery = add("symbol" -> currency)

  def side(value: String): BinanceQuery = add("side" -> value)

  def _type(value: String): BinanceQuery = add("type" -> value)

  def coin(value: String): BinanceQuery = add("coin" -> value)

  def withdrawOrderId(value: String): BinanceQuery = add("withdrawOrderId" -> value)

  def network(value: String): BinanceQuery = add("network" -> value)

  def address(value: String): BinanceQuery = add("address" -> value)

  /** Secondary address identifier for coins like XRP,XMR etc. */
  def addressTag(value: String): BinanceQuery = add("addressTag" -> value)

  def amount(value: Double): BinanceQuery = add("amount" -> value)

  /**
   * When making internal transfer:
   * - true for returning the fee to the destination account
   * - false for returning the fee back to the departure account.
   * Default false
   */
  def transactionFeeFlag(value: Boolean): BinanceQuery = add("transactionFeeFlag" -> value)

  /** Description of the address. Space in name should be encoded into %20 */
  def name(value: String): BinanceQuery = add("name" -> value)

  /**
   * The wallet type for withdraw，0-spot wallet ，1-funding wallet.
   * Default walletType is the current "selected wallet" under wallet -> Fiat and Spot/Funding -> Deposit
   * */
  def walletType(value: Int): BinanceQuery = add("walletType" -> value)

  def timeInForce(value: String): BinanceQuery = add("timeInForce" -> value)

  def txId(value: String): BinanceQuery = add("txId" -> value)

  /** 0 (0:pending,6: credited but cannot withdraw, 1:success) */
  def status(value: Int): BinanceQuery = add("status" -> value)

  def offset(value: Int): BinanceQuery = add("offset" -> value)

  /** Default:1000, Max:1000 */
  def limit: BinanceQuery = add("limit" -> 1000)

  def limit(value: Int): BinanceQuery = add("limit" -> value)

  def price(value: Double): BinanceQuery = add("price" -> value)

  def quantity(value: Int): BinanceQuery = add("quantity" -> value)

  def recvWindow(value: Long): BinanceQuery = add("recvWindow" -> value)

  private def signature: BinanceTask[BinanceQuery] =
    bclient =>
      for {
        hash <- Hash.hmac256(show())(bclient)
      } yield add("signature" -> hash)

  def formalize: BinanceTask[BinanceQuery] =
    self.time match {
      case Some(_) => signature
      case None => _ => self.pure[IO]
    }

  def end: BinanceTask[BinanceQuery] = formalize

  def ? : BinanceTask[BinanceQuery] = formalize

  def ?> : BinanceTask[BinanceQuery] = signature

  def hmac: BinanceTask[BinanceQuery] = signature

  def show(): String = options.mkString("&")

  override def toString: String = show()
}

object BinanceQuery {

  private val empty: BinanceQuery = BinanceQuery(Set.empty)

  def build: BinanceQuery = empty

  def build[T](kv: (String, T)*): BinanceQuery = set(empty, kv: _*)

  def build[T](ops: Set[QueryOption[T]]): BinanceQuery = set(empty, ops)

  def diff(old: BinanceQuery, newq: BinanceQuery): BinanceQuery = {
    val diffOpts = old.options.filterNot(q => newq.keys.contains(q.key))
    BinanceQuery(diffOpts)
  }

  def append(qr1: BinanceQuery, qr2: BinanceQuery): BinanceQuery = {
    val diffqr = diff(qr1, qr2)
    BinanceQuery(diffqr.options ++ qr2.options)
  }

  def set[T](qr: BinanceQuery, kv: (String, T)*): BinanceQuery = {
    val newOptions: Set[QueryOption[String]] = kv.map { case (k, v) => QueryOption(k, v).formalize() }.toSet
    val newqr: BinanceQuery = BinanceQuery(newOptions)
    append(qr, newqr)
  }

  def set[T](qr: BinanceQuery, ops: Set[QueryOption[T]]): BinanceQuery = {
    val newqr = BinanceQuery(ops.map(_.formalize()))
    append(qr, newqr)
  }


  def merge(qrs: BinanceQuery*): BinanceQuery = qrs.reduceLeft(append)
}
