package dev.rafafrdz.binance.examples

import dev.rafafrdz.binance.core.config._
import dev.rafafrdz.binance.core.session._

/** This is an example where show how to create a BinanceContext by the simplest way */
object BinanceContextEx {

  /** Option 1
   * 1 - Create a BinanceContext from a conf file
   * */

  val bcntx1: BinanceContext =
    BinanceContext
      .build()
      .from("config/application.conf")
      .create()


  /** Option 2
   * 1 - Create a BinanceConfig from a conf file
   * 2 - Set the BinanceConfig to BinanceContext with from method
   * */

  val bcnf: BinanceConfig =
    BinanceConfig
      .from("config/application.conf")
      .define()

  val bcntx2: BinanceContext =
    BinanceContext
      .build()
      .from(bcnf)
      .create()

  /** Option 3
   * 1 - Create a BinanceConfig by settings options
   * 2 - Set the BinanceConfig to BinanceContext with from method
   * */

  val bcnfOpts: BinanceConfig =
    BinanceConfig
      .build()
      .set(
        "binance.credential.access.key" -> "access-key"
        , "binance.credential.secret.key" -> "secret-key"
        , "binance.api.mode" -> "test"
      )
      .define()

  val bcntx3: BinanceContext =
    BinanceContext
      .build()
      .from(bcnfOpts)
      .create()
}
