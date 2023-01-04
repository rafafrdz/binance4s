package binance1.api

import io.github.rafafrdz.binance1.core.config.TypeCheck.domain
import io.github.rafafrdz.binance1.core.config.TypeCheck
import io.github.rafafrdz.binance1.core.session.options.BinanceAPIUri
import io.github.rafafrdz.binance1.core.session.security.Credential

object implicits {
  object types {
    implicit val booltc: TypeCheck[Boolean] = domain.BooleanType
    implicit val inttc: TypeCheck[Int] = domain.IntType
    implicit val stringtc: TypeCheck[String] = domain.StringType
    implicit val apitc: TypeCheck[BinanceAPIUri] = domain.ApiType
    implicit val credtc: TypeCheck[Credential] = domain.CredentialType
  }

}
