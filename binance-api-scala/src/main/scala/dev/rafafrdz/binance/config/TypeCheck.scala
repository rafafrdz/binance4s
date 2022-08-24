package dev.rafafrdz.binance.config

import dev.rafafrdz.binance.session.options.BinanceAPIUri

sealed trait TypeCheck[T]


object TypeCheck {

  /** Todo. Add more element types o todo another implementation to map implictly types */
  object domain {
    case object BooleanType extends TypeCheck[Boolean]

    case object IntType extends TypeCheck[Int]

    case object StringType extends TypeCheck[String]

    case object ApiType extends TypeCheck[BinanceAPIUri]
  }

}
