package io.github.rafafrdz.binance.api.query

import cats.effect.IO
import cats.implicits._
import io.github.rafafrdz.binance.BinanceTask
import io.github.rafafrdz.binance.api.utils
import io.github.rafafrdz.binance.security.Hash


case class BinanceQuery private(options: Set[QueryOption[String]], time: Option[Long] = None) {
  self =>

  def keys: Set[String] = options.map(_.key)

  def add[T](kv: (String, T)*): BinanceQuery = BinanceQuery.set(self, kv: _*)

  def add(ops: Set[QueryOption[String]]): BinanceQuery = BinanceQuery.set(self, ops)

  def merge(other: BinanceQuery*): BinanceQuery = BinanceQuery.merge(self +: other: _*)

  /** Methods for specifics options in the query */

  def timestamp: BinanceQuery = {
    lazy val t: Long = System.currentTimeMillis()
    add("timestamp" -> t).copy(time = Option(t))
  }

  def startTime(time: Long): BinanceQuery = add("startTime" -> time)

  def startTime(timestamp: String): BinanceQuery = startTime(utils.timestamp.parse(timestamp).getTime)

  def endTime(time: Long): BinanceQuery = add("endTime" -> time)

  def endTime: BinanceQuery = endTime(System.currentTimeMillis())

  def endTime(timestamp: String): BinanceQuery = endTime(utils.timestamp.parse(timestamp).getTime)

  def symbol(currency: String): BinanceQuery = add("symbol" -> currency)

  private def signature: BinanceTask[BinanceQuery] =
    bclient =>
      for {
        hash <- Hash.hmec256(show())(bclient)
      } yield add("signature" -> hash)

  def formalize: BinanceTask[BinanceQuery] =
    self.time match {
      case Some(_) => signature
      case None => _ => self.pure[IO]
    }

  def end: BinanceTask[BinanceQuery] = formalize
  def ? : BinanceTask[BinanceQuery] = formalize

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
