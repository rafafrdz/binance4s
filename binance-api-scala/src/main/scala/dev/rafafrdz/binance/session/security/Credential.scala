package dev.rafafrdz.binance.session.security

import dev.rafafrdz.binance.api.implicits.types._
import dev.rafafrdz.binance.config.BinanceConfig
import dev.rafafrdz.binance.session.BinanceOption


sealed trait Credential extends BinanceOption {
  self =>
  private[session] val refOption: String = Credential.refOption

  def getAccessKey: Option[String] = None
}


object Credential {
  private[session] val refOption: String = "credential"

  private[session] case class CredentialProvived(accesKey: String, secretKey: String) extends Credential {
    override def getAccessKey: Option[String] = Option(accesKey)
  }

  private[session] case object NonProvided extends Credential

  def from(bconf: BinanceConfig): Credential = {
    val accessKey: Option[String] = bconf.get[String](s"$refOption", "access", "key")
    val secretKey: Option[String] = bconf.get[String](s"$refOption", "secret", "key")
    create(accessKey, secretKey)
  }

  def create(accessKey: String, secretKey: String): Credential =
    CredentialProvived(accessKey, secretKey)

  def create(accessKey: Option[String], secretKey: Option[String]): Credential = {
    (accessKey, secretKey) match {
      case (Some(k), Some(s)) => CredentialProvived(k, s)
      case _ => NonProvided
    }

  }
}
