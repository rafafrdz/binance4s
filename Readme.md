
# Binance API Scala

![binance-logo](https://binance-docs.github.io/apidocs/spot/en/images/logo.svg)

![](https://img.shields.io/badge/dev-wip-yellow?logo=github&logoColor=white)
![](https://img.shields.io/badge/release-v2.13/1.1.2-gree)

A functional Binance API built on Scala language.

### Introduction

Binance offers access to Spot, Margin, Futures and Options API trading for over 300 Digital and Fiat currencies.
API trading provides a testing environment, API documentation, and Sample Code in 6 languages.
Suitable for HFT, Social Trader and Strategy trader. 

**Link:** [Binance API Doc](https://binance-docs.github.io/apidocs/spot/en/#introduction)

## How to take a look in Postman
Binance offers several Postman Collections and Environments (JSON files) for a quicker and easier usage of our RESTful APIs. See documentation: [Binance API Postman (Doc)](https://github.com/binance/binance-api-postman)

## Getting started
### Properties
```
binance.credential.access.key = ${?BINANCE_CREDENTIAL_ACCESS_KEY}
binance.credential.secret.key = ${?BINANCE_CREDENTIAL_SECRET_KEY}
binance.api.mode = ${?BINANCE_API_MODE}
```

### Example of a simple Binance request

```scala
package dev.rafafrdz.binance.examples

import dev.rafafrdz.binance._
import dev.rafafrdz.binance.api.options._
import dev.rafafrdz.binance.api.reqest.get._
import dev.rafafrdz.binance.core.session._
import requests.Response

object BinanceAppEx {

  /** BinanceContext */
  val bcntx: BinanceContext =
    BinanceContext
      .build()
      .from("config/application.conf")
      .create()

  /** BinanceQuery */
  val query: BinanceQuery =
    QueryRequest
      .build()
      .timestamp()
      .signature

  /** BinanceRequest */
  val request: BinanceRequest =
    depositHistory(query)

  /** Response */
  val response: Option[Response] =
    request(bcntx)


}

```

### More examples
You could find more examples in the example folder.

**Link**. [[Repo] Binance Folder Examples](https://github.com/rafafrdz/binance-api-scala/blob/release-2.13-0.1/binance-api-scala/src/main/scala/dev/rafafrdz/binance/examples)

**Link**. [[Repo] Binance App Example](https://github.com/rafafrdz/binance-api-scala/blob/release-2.13-0.1/binance-api-scala/src/main/scala/dev/rafafrdz/binance/examples/BinanceAppEx.scala)

**Link**. [[Repo] Binance Context Example](https://github.com/rafafrdz/binance-api-scala/blob/release-2.13-0.1/binance-api-scala/src/main/scala/dev/rafafrdz/binance/examples/BinanceContextEx.scala)

**Link**. [[Repo] Binance Query Example](https://github.com/rafafrdz/binance-api-scala/blob/release-2.13-0.1/binance-api-scala/src/main/scala/dev/rafafrdz/binance/examples/BinanceQueryEx.scala)

**Link**. [[Repo] Binance Request Example](https://github.com/rafafrdz/binance-api-scala/blob/release-2.13-0.1/binance-api-scala/src/main/scala/dev/rafafrdz/binance/examples/BinanceRequestEx.scala)


