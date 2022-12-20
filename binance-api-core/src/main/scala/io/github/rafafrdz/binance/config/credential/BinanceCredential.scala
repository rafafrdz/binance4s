package io.github.rafafrdz.binance.config.credential

import io.github.rafafrdz.binance.config.option.{BinanceOption, BinanceOptionT}

sealed trait BinanceCredential extends BinanceOption {
  override val ref: String = "credential"
}

object BinanceCredential {

  val ref: String = "binance.credential"

  val AccessKey: String = "access.key"

  val SecretKey: String = "secret.key"

  implicit val oauthAlgebra: BinanceOptionT[OAuth] =
    BinanceOptionT.makeOpt(OAuth.parser, OAuth.serializer)

}

case class OAuth(accessKey: String, secretKey: String) extends BinanceCredential

object OAuth {

  def default: OAuth = OAuth("<empty>", "<empty>")

  def parser(ref: String): Option[OAuth] = {
    val accessKey: Option[String] = BinanceOptionT.getSysOrEnv(s"$ref.${BinanceCredential.AccessKey}")
    val secretKey: Option[String] = BinanceOptionT.getSysOrEnv(s"$ref.${BinanceCredential.SecretKey}")
    for {
      acc <- accessKey
      scr <- secretKey
    } yield OAuth(acc, scr)
  }

  def serializer(opt: OAuth): Map[String, String] =
    Map(
      s"${BinanceCredential.ref}.${BinanceCredential.AccessKey}" -> opt.accessKey,
      s"${BinanceCredential.ref}.${BinanceCredential.SecretKey}" -> opt.secretKey
    )
}

