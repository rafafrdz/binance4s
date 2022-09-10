package io.github.rafafrdz.binance.core.config

import io.github.rafafrdz.binance.core.session.options.BinanceAPIUri
import io.github.rafafrdz.binance.core.session.security.Credential

sealed trait TypeCheck[T] extends Product with Serializable


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
