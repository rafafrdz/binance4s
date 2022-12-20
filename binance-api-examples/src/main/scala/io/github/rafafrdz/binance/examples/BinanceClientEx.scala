package io.github.rafafrdz.binance.examples

import io.github.rafafrdz.binance.client.BinanceClient
import io.github.rafafrdz.binance.config.BinanceConfig

/** This is an example where show how to create a BinanceClient by the simplest way */
object BinanceClientEx {

  /** Option 0
   * 1 - Create a BinanceClient with default (empty) settings
   * */

  lazy val bclient0: BinanceClient =
    BinanceClient
      .build
      .create()

  /** Option 1
   * 1 - Create a BinanceClient with environment settings (automatically)
   * */

  lazy val bclient1: BinanceClient =
    BinanceClient
      .build
      .credential
      .test // .api or .test or .mode(<mode>)
      .create()


  /** Option 2
   * 1 - Create a BinanceConfig from a conf file
   * 2 - Set the BinanceConfig to BinanceClient with from method
   * */

  lazy val bcnf: BinanceConfig =
    BinanceConfig
      .from("config/application.conf")

  lazy val bclient2: BinanceClient =
    BinanceClient
      .build
      .from(bcnf)
      .create()

  /** Option 3
   * 1 - Create a BinanceConfig by settings options
   * 2 - Set the BinanceConfig to BinanceClient with from method
   * */

  lazy val bcnfOpts: BinanceConfig =
    BinanceConfig
      .default
      .set(
        "binance.credential.access.key" -> "access-key"
        , "binance.credential.secret.key" -> "secret-key"
        , "binance.api.mode" -> "test"
      )

  lazy val bclient3: BinanceClient =
    BinanceClient
      .build
      .from(bcnfOpts)
      .create()

}
