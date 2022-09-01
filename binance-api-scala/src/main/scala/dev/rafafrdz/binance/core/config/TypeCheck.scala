package dev.rafafrdz.binance.core.config

import dev.rafafrdz.binance.core.session.options.BinanceAPIUri
import dev.rafafrdz.binance.core.session.security.Credential

sealed trait TypeCheck[T]


object TypeCheck {

  /** Todo. Add more element types o todo another implementation to map implictly types */
  object domain {
    case object BooleanType extends TypeCheck[Boolean]

    case object IntType extends TypeCheck[Int]

    case object StringType extends TypeCheck[String]

    case object ApiType extends TypeCheck[BinanceAPIUri]
    case object CredentialType extends TypeCheck[Credential]
  }

}
