package dev.rafafrdz.binance.core.config

import com.typesafe.config.{Config, ConfigFactory}
import dev.rafafrdz.binance.core.session.options.BinanceAPIUri
import dev.rafafrdz.binance.core.session.security.Credential

import scala.util.Try

trait BinanceConfig {
  self =>
  val conf: Option[Config] = None

  def get[T: TypeCheck](key: String*): Option[T] = BinanceConfig.get[T](self, key: _*)

  def getOrElse[T: TypeCheck](key: String*)(orElse: => T): T = BinanceConfig.getOrElse[T](self, orElse, key: _*)

  def set(options: (String, String)*): BinanceConfigBuilder = BinanceConfigBuilder.set(self, options: _*)

  def set(options: Map[String, String]): BinanceConfigBuilder = BinanceConfigBuilder.set(self)(options)

  def merge(other: BinanceConfig): BinanceConfig = BinanceConfig.merge(self, other)


}

object BinanceConfig {

  import TypeCheck.domain._

  private[core] val BINANCE: String = "binance"

  private[core] val empty: BinanceConfig = new BinanceConfig {}

  def build(): BinanceConfigBuilder = BinanceConfigBuilder.empty

  /**
   * Get BinanceConfigBuilder object by Config object.
   * For instance: If the configuration file is allocated in path/subpath/file.conf
   * you can call by getFrom("path/subpath/file") or getFrom("path/subpath/file.conf")
   *
   * @param config Config
   * @return BinanceConfig
   */
  def from(config: Config): BinanceConfigBuilder = new BinanceConfigBuilder {
    override val conf: Option[Config] = Some(config)
  }

  /**
   * Get BinanceConfig object by path.
   * For instance: If the configuration file is allocated in path/subpath/file.conf
   * you can call by getFrom("path/subpath/file") or getFrom("path/subpath/file.conf")
   *
   * @param path Path
   * @return BinanceConfig
   */
  def from(path: String): BinanceConfigBuilder = {
    val parsed = Try(ConfigFactory.load(path).getConfig(BinanceConfig.BINANCE)).toOption
    parsed match {
      case Some(conf) => from(conf)
      case None => BinanceConfigBuilder.empty
    }
  }

  private def parse[T](cparsed: String => Any)(path: String): Option[T] = Try(cparsed(path).asInstanceOf[T]).toOption

  private def getOrElse[T](bconf: BinanceConfig, orElse: => T, key: String*)(implicit t: TypeCheck[T]): T =
    get[T](bconf, key: _*) match {
      case Some(value) => value
      case None => orElse
    }

  private def get[T](bconf: BinanceConfig, key: String*)(implicit t: TypeCheck[T]): Option[T] = {
    val path: String = key.mkString(".").replaceFirst("binance.", "") // credential.api.key


    bconf.conf match {
      case Some(cfg) => t match {
        case BooleanType => parse(cfg.getBoolean)(path)
        case IntType => parse(cfg.getInt)(path)
        case StringType => parse(cfg.getString)(path)
        case ApiType => parse(BinanceAPIUri.get.compose(cfg.getString))(path)
        case CredentialType => parse(Credential.get(bconf))(path)
      }
      case None => None
    }
  }

  def merge(left: BinanceConfig, right: BinanceConfig): BinanceConfig = {
    new BinanceConfig {
      override val conf: Option[Config] = (left.conf, right.conf) match {
        case (Some(l), Some(r)) => Option(l.resolveWith(r))
        case (None, r) => r
        case (l, None) => l
      }
    }
  }
}
