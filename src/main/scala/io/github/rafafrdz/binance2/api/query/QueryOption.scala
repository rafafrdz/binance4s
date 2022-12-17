package io.github.rafafrdz.binance2.api.query

case class QueryOption[T](key: String, value: T) {

  override def toString: String = s"$key=$value"

  def sameRef(other: QueryOption[T]): Boolean = key == other.key

  def formalize(): QueryOption[String] = QueryOption(key, value.toString)

  override def equals(obj: Any): Boolean = {
    obj match {
      case QueryOption(k, v) => k == key & v == value
      case _ => false
    }
  }
}
