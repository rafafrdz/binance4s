package dev.rafafrdz.binance.api

import dev.rafafrdz.binance.core.config.TypeCheck
import dev.rafafrdz.binance.core.config.TypeCheck.domain
import dev.rafafrdz.binance.core.session.options.BinanceAPIUri
import dev.rafafrdz.binance.core.session.security.Credential

object implicits {
  object types {
    implicit val booltc: TypeCheck[Boolean] = domain.BooleanType
    implicit val inttc: TypeCheck[Int] = domain.IntType
    implicit val stringtc: TypeCheck[String] = domain.StringType
    implicit val apitc: TypeCheck[BinanceAPIUri] = domain.ApiType
    implicit val credtc: TypeCheck[Credential] = domain.CredentialType
  }

}
