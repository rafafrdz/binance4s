package io.github.rafafrdz.binance.api.request

import cats.effect.IO
import io.github.rafafrdz.binance.BinanceTask
import io.github.rafafrdz.binance.api.EntityIODecoder
import io.github.rafafrdz.binance.api.uri.BinanceURI
import org.http4s.ember.client.EmberClientBuilder
import org.http4s.{Header, Headers, Method, Request, Uri}
import org.typelevel.ci.CIString

private[api] object BinanceExecute {

  def execute[T: EntityIODecoder](method: Method, task: BinanceTask[BinanceURI]): BinanceTask[T] = cl =>
    for {
      binanceUri <- cl.run(task)
      binanceMode <- cl.config.mode
      uri <- IO(Uri.unsafeFromString(binanceUri.mode(binanceMode).show()))
      hd <- cl.run(headers.api)
      request = Request[IO](method = method, uri = uri, headers = Headers(hd))
      response <- execute(request)
    } yield response

  def execute[T: EntityIODecoder](request: Request[IO]): IO[T] =
    EmberClientBuilder.default[IO].build.use(client => client.expect[T](request))


  private object headers {

    val XMBXApiKey: String = "X-MBX-APIKEY"

    def api: BinanceTask[Header.Raw] = bnc => bnc.config.credential.map(cr => simple(XMBXApiKey, cr.accessKey))

    def simple(name: String, data: String): Header.Raw = Header.Raw(CIString(name), data)
  }


}