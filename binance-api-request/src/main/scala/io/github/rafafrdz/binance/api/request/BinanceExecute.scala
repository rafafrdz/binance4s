package io.github.rafafrdz.binance.api.request

import cats.effect.IO
import io.github.rafafrdz.binance.BinanceTask
import io.github.rafafrdz.binance.api.uri.BinanceUri
import org.http4s.circe.CirceEntityDecoder
import org.http4s.dsl.io.GET
import org.http4s.ember.client.EmberClientBuilder
import org.http4s.{EntityDecoder, Method, Request, Uri}

trait BinanceExecute { //extends CirceEntityDecoder {

  type EntityIODecoder[T] = EntityDecoder[IO, T]

  def execute[T: EntityIODecoder](task: BinanceTask[BinanceUri]): BinanceTask[T] = execute(GET, task)

  def execute[T: EntityIODecoder](method: Method, task: BinanceTask[BinanceUri]): BinanceTask[T] = cl =>
    for {
      binanceUri <- cl.run(task)
      uri = Uri.unsafeFromString(binanceUri.show())
      request = Request[IO](method = method, uri = uri)
      json <- execute(request)
    } yield json

  def execute[T: EntityIODecoder](request: Request[IO]): IO[T] =
    EmberClientBuilder.default[IO].build.use(client => client.expect[T](request))

}

//object BinanceExecute extends CirceEntityDecoder