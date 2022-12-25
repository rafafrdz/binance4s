package io.github.rafafrdz.binance.api.request

import cats.effect._
import io.github.rafafrdz.binance.BinanceTask
import io.github.rafafrdz.binance.api.query.BinanceQuery
import io.github.rafafrdz.binance.client.BinanceClient
import io.github.rafafrdz.binance.config.mode.Test
import org.http4s.EntityDecoder.byteVector
import org.http4s.dsl.io.POST
import org.http4s.ember.client.EmberClientBuilder
import org.http4s.{Query, Request, Uri}
import scodec.bits.ByteVector


object PostScratch extends IOApp.Simple {

  val testURI: Uri = Uri.unsafeFromString(Test.uri)
  val postUri: Uri = testURI / "api" / "v3" / "order"
  val client =
    BinanceClient
      .build
      .test
      .credential
      .create()

  val queryPost: BinanceTask[BinanceQuery] =
    BinanceQuery.build
      .symbol("LTCBTC")
      .side("BUY")
      ._type("LIMIT")
      .timeInForce("GTC")
      .quantity(1)
      .price(0.1)
      .recvWindow(5000)
      .timestamp.?

  val queryPostCompleted: BinanceTask[ByteVector] = bclient =>
    for {
      query <- queryPost(bclient)
      uriCompleted = postUri.copy(query = Query.unsafeFromString(query.show()))
      postRequested = Request[IO](POST).withUri(uriCompleted)
      res <- doPost(postRequested)
    } yield res


  val post: Request[IO] = Request[IO](POST).withUri(postUri)

  def doPost(postRequest: Request[IO]): IO[ByteVector] =
    EmberClientBuilder
      .default[IO]
      .build
      .use { client => client.expect(postRequest) }

  override def run: IO[Unit] =
    for {
      dd <- queryPostCompleted(client)
      _ <- IO.println(dd)
    } yield ()
}
