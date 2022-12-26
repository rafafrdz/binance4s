package io.github.rafafrdz.binance.config.credential

import com.typesafe.config.Config
import io.github.rafafrdz.binance.config.option.{BinanceOption, BinanceOptionT}

import scala.util.Try

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
    val oauthOpt: Option[OAuth] =
      for {
        acc <- accessKey
        scr <- secretKey
      } yield OAuth(acc, scr)
    oauthOpt.orElse(Option(default))
  }

  def serializer(opt: OAuth): Map[String, String] =
    Map(
      s"${BinanceCredential.ref}.${BinanceCredential.AccessKey}" -> opt.accessKey,
      s"${BinanceCredential.ref}.${BinanceCredential.SecretKey}" -> opt.secretKey
    )

  def from(conf: Config): OAuth = {
    val oautOpt: Try[OAuth] = for {
      accOpt <- Try(conf.getString(s"${BinanceCredential.ref}.${BinanceCredential.AccessKey}"))
      secrOpt <- Try(conf.getString(s"${BinanceCredential.ref}.${BinanceCredential.SecretKey}"))
    } yield OAuth(accOpt, secrOpt)
    oautOpt.getOrElse(OAuth.default)
  }

  def from(map: Map[String, String]): Option[OAuth] = {
    val oautOpt: Option[OAuth] = for {
      accOpt <- map.get(s"${BinanceCredential.ref}.${BinanceCredential.AccessKey}")
      secrOpt <- map.get(s"${BinanceCredential.ref}.${BinanceCredential.SecretKey}")
    } yield OAuth(accOpt, secrOpt)
    oautOpt
  }
}

