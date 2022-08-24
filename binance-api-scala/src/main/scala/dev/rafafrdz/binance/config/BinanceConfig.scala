package dev.rafafrdz.binance.config

import com.typesafe.config.{Config, ConfigFactory, ConfigValueFactory}
import dev.rafafrdz.binance.session.options.BinanceAPIUri

import scala.util.Try

trait BinanceConfig {
  self =>
  protected val conf: Option[Config] = None

  def get[T: TypeCheck](key: String*): Option[T] = BinanceConfig.get[T](self, key: _*)

  def set(key: String*)(value: String): BinanceConfig = BinanceConfig.set(self, key: _*)(value)

  def set(options: Map[String, String]): BinanceConfig = BinanceConfig.set(self)(options)

  def getOrElse[T: TypeCheck](key: String*)(orElse: => T): T = BinanceConfig.getOrElse[T](self, orElse, key: _*)


}

object BinanceConfig {

  import TypeCheck.domain._

  private val BINANCE: String = "binance"

  val empty: BinanceConfig = new BinanceConfig {}

  /**
   * Get BinanceConfig object by path.
   * For instance: If the configuration file is allocated in path/subpath/file.conf
   * you can call by getFrom("path/subpath/file") or getFrom("path/subpath/file.conf")
   *
   * @param path Path
   * @return BinanceConfig
   */
  def getFrom(path: String): BinanceConfig = {
    val parsed = Try(ConfigFactory.load(path).getConfig(BINANCE)).toOption
    parsed match {
      case Some(conf) => getFrom(conf)
      case None => empty
    }
  }

  /**
   * Get BinanceConfig object by Config object.
   * For instance: If the configuration file is allocated in path/subpath/file.conf
   * you can call by getFrom("path/subpath/file") or getFrom("path/subpath/file.conf")
   *
   * @param config Config
   * @return BinanceConfig
   */
  def getFrom(config: Config): BinanceConfig = new BinanceConfig {
    override protected val conf: Option[Config] = Some(config)
  }

  private def parse[T](cparsed: String => Any)(path: String): Option[T] = Try(cparsed(path).asInstanceOf[T]).toOption

  private def getOrElse[T](bconf: BinanceConfig, orElse: => T, key: String*)(implicit t: TypeCheck[T]): T =
    get[T](bconf, key: _*) match {
      case Some(value) => value
      case None => orElse
    }

  private def get[T](bconf: BinanceConfig, key: String*)(implicit t: TypeCheck[T]): Option[T] = {
    lazy val path: String = key.mkString(".")

    bconf.conf match {
      case Some(cfg) => t match {
        case BooleanType => parse(cfg.getBoolean)(path)
        case IntType => parse(cfg.getInt)(path)
        case StringType => parse(cfg.getString)(path)
        case ApiType => parse(BinanceAPIUri.get.compose(cfg.getString))(path)
      }
      case None => None
    }
  }


  def set(bconf: BinanceConfig)(options: Map[String, String]): BinanceConfig = {
    lazy val optionsConf = options.map { case (key, value) => (conf: Config) => conf.withValue(key, ConfigValueFactory.fromAnyRef(value)) }
    new BinanceConfig {
      override protected val conf: Option[Config] = bconf.conf match {
        case Some(value) => Option(optionsConf.foldLeft(value) { case (conf, confF) => confF(conf) })
        case None => Option(optionsConf.foldLeft(ConfigFactory.load()) { case (conf, confF) => confF(conf) })
      }
    }
  }

  def set(bconf: BinanceConfig, key: String*)(value: String): BinanceConfig = {
    val path: String = key.mkString(".")
    set(bconf)(Map(path -> value))
  }
}
