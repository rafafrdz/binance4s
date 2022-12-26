package io.github.rafafrdz.binance.api

import cats.effect.IO
import io.github.rafafrdz.binance.BinanceTask
import io.github.rafafrdz.binance.api.request.BinanceExecute
import io.github.rafafrdz.binance.api.uri.BinanceURI
import io.github.rafafrdz.binance.client.BinanceClient
import org.http4s.Method
import org.http4s.dsl.io.{GET, POST}

object implicits {

  implicit class BinanceClientRequest(bnc: BinanceClient) {

    def execute[T: EntityIODecoder](task: BinanceTask[BinanceURI]): IO[T] = get[T](task)

    def execute[T: EntityIODecoder](method: Method, task: BinanceTask[BinanceURI]): IO[T] =
      bnc.run(BinanceExecute.execute(method, task))

    def get[T: EntityIODecoder](task: BinanceTask[BinanceURI]): IO[T] = execute[T](GET, task)

    def post[T: EntityIODecoder](task: BinanceTask[BinanceURI]): IO[T] = execute[T](POST, task)
  }

}
