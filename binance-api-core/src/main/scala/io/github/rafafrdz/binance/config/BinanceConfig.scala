package io.github.rafafrdz.binance.config

import cats.effect.IO
import cats.implicits._
import com.typesafe.config.{Config, ConfigFactory}
import io.github.rafafrdz.binance.config.credential.OAuth
import io.github.rafafrdz.binance.config.mode.{BinanceMode, Test}

import scala.util.Try

trait BinanceConfig {
  self =>

  def credential: IO[OAuth]

  def mode: IO[BinanceMode]

  def set(opts: (String, String)*): BinanceConfig = new BinanceConfig {
    lazy val mapOpts: Map[String, String] = opts.toMap

    override def credential: IO[OAuth] = OAuth.from(mapOpts) match {
      case Some(value) => value.pure[IO]
      case None => self.credential
    }

    override def mode: IO[BinanceMode] = BinanceMode.from(mapOpts) match {
      case Some(value) => value.pure[IO]
      case None => self.mode
    }
  }

}

object BinanceConfig {

  def default: BinanceConfig = new BinanceConfig {

    override def credential: IO[OAuth] = IO.pure(OAuth.default)

    override def mode: IO[BinanceMode] = IO.pure(Test)
  }

  def from(path: String): BinanceConfig = {
    val bconfOpt: Try[BinanceConfig] = for {
      conf <- Try(ConfigFactory.load(path))
      bconf = from(conf)
    } yield bconf
    bconfOpt.getOrElse(BinanceConfig.default)
  }

  def from(conf: Config): BinanceConfig = new BinanceConfig {
    override def credential: IO[OAuth] = OAuth.from(conf).pure[IO]

    override def mode: IO[BinanceMode] = BinanceMode.from(conf).pure[IO]
  }


}