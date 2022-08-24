package dev.rafafrdz.binance.api

import dev.rafafrdz.binance.config.TypeCheck
import dev.rafafrdz.binance.config.TypeCheck.domain
import dev.rafafrdz.binance.session.options.BinanceAPIUri

object implicits {
  object types {
    implicit val booltc: TypeCheck[Boolean] = domain.BooleanType
    implicit val inttc: TypeCheck[Int] = domain.IntType
    implicit val stringtc: TypeCheck[String] = domain.StringType
    implicit val apitc: TypeCheck[BinanceAPIUri] = domain.ApiType
  }

}
