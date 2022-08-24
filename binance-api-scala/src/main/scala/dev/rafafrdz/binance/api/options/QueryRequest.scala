package dev.rafafrdz.binance.api.options

import dev.rafafrdz.binance.BinanceOptionTask
import dev.rafafrdz.binance.api.options.utils.ApiUtils
import dev.rafafrdz.binance.session.BinanceContext
import dev.rafafrdz.binance.session.security.Hash

import scala.util.Try

case class QueryOption(key: String, value: String) {
  override def toString: String = s"$key=$value"

  def sameRef(other: QueryOption): Boolean = key == other.key

  override def equals(obj: Any): Boolean = {
    obj match {
      case QueryOption(k, v) => k == key & v == value
      case _ => false
    }
  }
}

case class QueryRequest private(options: Set[QueryOption], time: Option[Long] = None) {
  self =>

  def keys: Set[String] = options.map(_.key)

  def add[T](kv: (String, T)*): QueryRequest = QueryRequest.set(self, kv: _*)

  def add(ops: Set[QueryOption]): QueryRequest = QueryRequest.set(self, ops)

  def merge(other: QueryRequest*): QueryRequest = QueryRequest.merge(self +: other: _*)

  /** Methods for specifics options in the query */

  def timestamp(): QueryRequest = {
    val t: Long = System.currentTimeMillis()
    add("timestamp" -> t).copy(time = Option(t))
  }

  def startTime(time: Long): QueryRequest = add("startTime" -> time)

  def startTime(timestamp: String): QueryRequest = startTime(ApiUtils.timestamp.parse(timestamp).getTime)

  def endTime(time: Long): QueryRequest = add("endTime" -> time)

  def endTime(timestamp: String): QueryRequest = endTime(ApiUtils.timestamp.parse(timestamp).getTime)

  def symbol(currency: String): QueryRequest = add("symbol" -> currency)

  def signature: BinanceOptionTask[QueryRequest] =
    (bctx: BinanceContext) => {
      lazy val sig = Hash.hmec256(show())(bctx)
      time match {
        case Some(_) => sig.flatMap { sign => Try(add("signature" -> sign)).toOption }

        /** Esto es asumiendo que siempre se necesite el timestamp, si esto no es asi,
         * deberia devolver la signature sin el timestamp */
        case None => timestamp().signature(bctx)
      }
    }

  def formalize(): BinanceOptionTask[QueryRequest] = _ => Option(self)

  def show(): String = options.mkString("&")

  override def toString: String = show()
}

object QueryRequest {
  private val empty: QueryRequest = QueryRequest(Set.empty)

  def build(): QueryRequest = empty

  def build[T](kv: (String, T)*): QueryRequest = set(empty, kv: _*)

  def build(ops: Set[QueryOption]): QueryRequest = set(empty, ops)

  def diff(old: QueryRequest, newq: QueryRequest): QueryRequest = {
    val diffOpts = old.options.filterNot(q => newq.keys.contains(q.key))
    QueryRequest(diffOpts)
  }

  def append(qr1: QueryRequest, qr2: QueryRequest): QueryRequest = {
    val diffqr = diff(qr1, qr2)
    QueryRequest(diffqr.options ++ qr2.options)
  }

  def set[T](qr: QueryRequest, kv: (String, T)*): QueryRequest = {
    val newOptions: Set[QueryOption] = kv.map { case (k, v) => QueryOption(k, v.toString) }.toSet
    val newqr: QueryRequest = QueryRequest(newOptions)
    append(qr, newqr)
  }

  def set(qr: QueryRequest, ops: Set[QueryOption]): QueryRequest = {
    val newqr = QueryRequest(ops)
    append(qr, newqr)
  }


  def merge(qrs: QueryRequest*): QueryRequest = qrs.reduceLeft(append)
}