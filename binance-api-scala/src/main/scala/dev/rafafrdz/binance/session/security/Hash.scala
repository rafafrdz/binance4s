package dev.rafafrdz.binance.session.security

import com.roundeights.hasher.{Algo, Digest}
import dev.rafafrdz.binance.session.BinanceContext
import dev.rafafrdz.binance.{BinanceOptionTask, BinanceTask}

sealed trait Algorithm

object Hash {
  type HashFunction = Algorithm => Option[Digest]
  type Hashing[T] = T => HashFunction

  object Algorithm {
    case object SHA256 extends Algorithm

    case object SHA512 extends Algorithm
  }

  private def credentialHashF[T](secret: String): Hashing[T] = (data: T) => {
    case Algorithm.SHA256 => Option(Algo.hmac(secret).sha256(data.toString))
    case Algorithm.SHA512 => Option(Algo.hmac(secret).sha512(data.toString))
  }

  private def hashF[T]: Hashing[T] = (data: T) => {
    case Algorithm.SHA256 => Option(Algo.sha256(data.toString))
    case Algorithm.SHA512 => Option(Algo.sha512(data.toString))
    case _ => None
  }

  def hash[T]: BinanceTask[Hashing[T]] =
    (bctx: BinanceContext) => (data: T) => (algorithm: Algorithm) =>
      bctx.credential match {
        case Credential.CredentialProvived(_, secretKey) => credentialHashF[T](secretKey)(data)(algorithm)
        case Credential.NonProvided => hashF[T](data)(algorithm)
      }

  def hmec256[T](data: T): BinanceOptionTask[String] = (bctx: BinanceContext) => hash(bctx)(data)(Algorithm.SHA256).map(_.hex)

  def hmec512[T](data: T): BinanceOptionTask[String] = (bctx: BinanceContext) => hash(bctx)(data)(Algorithm.SHA512).map(_.hex)


  //  def hash(algo: Algo)(data: String): String = {
  //    val hash = algo(data)
  //    println("Hashed using " + algo.name + ": " + hash.hex)
  //    println("Matches: " + (algo(data).hash = hash))
  //    hash.hex
  //  }

}
