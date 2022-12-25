package io.github.rafafrdz.binance.api.request

import cats.effect._
import io.github.rafafrdz.binance.config.mode.Test
import org.http4s.Uri
import org.http4s.ember.client.EmberClientBuilder

object GetScratch extends IOApp.Simple {

  val testURI: Uri = Uri.unsafeFromString(Test.uri)
  val postUri: Uri = testURI / "api" / "v3" / "order"

  /** GET */
  val ping: IO[String] =
    EmberClientBuilder
      .default[IO]
      .build
      .use { client => client.expect[String](testURI / "api" / "v3" / "ping") }

  val checkServerTime: IO[String] =
    EmberClientBuilder
      .default[IO]
      .build
      .use { client => client.expect[String](testURI / "api" / "v3" / "time") }

  override def run: IO[Unit] =
    for {
      respPing <- ping
      respCheck <- checkServerTime
      _ <- IO.pure(println(respPing))
      _ <- IO.pure(println(respCheck))
      _ <- IO.pure(println(testURI))
    } yield ()
}
