package io.github.rafafrdz.binance.api.request

import cats.effect._
import org.http4s.ember.client.EmberClientBuilder

object Scratch extends IOApp.Simple {

   /** GET */
  val ping: IO[String] =
    EmberClientBuilder
      .default[IO]
      .build
      .use { client => client.expect[String]("https://testnet.binance.vision/api/v3/ping") }

  val checkServerTime: IO[String] =
    EmberClientBuilder
      .default[IO]
      .build
      .use { client => client.expect[String]("https://testnet.binance.vision/api/v3/time") }



  override def run: IO[Unit] =
    for {
      respPing <- ping
      respCheck <- checkServerTime
      _ <- IO.pure(println(respPing))
      _ <- IO.pure(println(respCheck))
    } yield ()
}
