package io.github.rafafrdz.binance2.security

import cats.effect.IO
import com.roundeights.hasher.{Algo, Digest}
import io.github.rafafrdz.binance2.BinanceTask
import io.github.rafafrdz.binance2.client.BinanceClient
import io.github.rafafrdz.binance2.config.credential.OAuth


object Hash {

  sealed trait Algorithm extends Product with Serializable

  type HashFunction[T, B] = T => Algorithm => B

  type Hashed[T, B] = HashFunction[T, IO[B]]

  type Hashing[T] = Hashed[T, Digest]

  object Algorithm {

    type SHA256 = SHA256.type

    type SHA512 = SHA512.type

    case object SHA256 extends Algorithm

    case object SHA512 extends Algorithm
  }

  def hmec256[T](data: T): BinanceTask[String] = hmec[T](data)(Algorithm.SHA256)

  def hmec512[T](data: T): BinanceTask[String] = hmec[T](data)(Algorithm.SHA512)

  def hmec[T]: HashFunction[T, BinanceTask[String]] =
    (data: T) => (algorithm: Algorithm) => (bclient: BinanceClient) =>
      hash[T](data)(algorithm)(bclient).map(digest => digest.hex)


  private def hashSecretF[T](secret: String): HashFunction[T, Digest] = (data: T) => {
    case Algorithm.SHA256 => Algo.hmac(secret).sha256(data.toString)
    case Algorithm.SHA512 => Algo.hmac(secret).sha512(data.toString)
  }

  private def hashF[T]: HashFunction[T, Digest] = (data: T) => {
    case Algorithm.SHA256 => Algo.sha256(data.toString)
    case Algorithm.SHA512 => Algo.sha512(data.toString)
  }

  private def hash[T]: HashFunction[T, BinanceTask[Digest]] =
    (data: T) => (algorithm: Algorithm) => (bclient: BinanceClient) => {
      bclient.config.credential.map(oauth =>
        if (oauth == OAuth.default) hashF(data)(algorithm)
        else hashSecretF(oauth.secretKey)(data)(algorithm)
      )
    }

}
