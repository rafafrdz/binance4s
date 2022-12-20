package io.github.rafafrdz.binance.config.option

import cats.effect.IO

import scala.util.Try

trait BinanceOptionT[O <: BinanceOption] {

  def parse(ref: String): IO[Option[O]]

  def serialize(bopt: O): IO[Map[String, String]]
}

object BinanceOptionT {

  def getSysOrEnv(key: String): Option[String] = sys.props.get(key).orElse(sys.env.get(key))

  private[config] def makeOpt[O <: BinanceOption]
  (parser: String => Option[O], serializer: O => Map[String, String]): BinanceOptionT[O] =
    new BinanceOptionT[O] {

      override def parse(ref: String): IO[Option[O]] =
        IO.pure(parser(ref))

      override def serialize(bopt: O): IO[Map[String, String]] =
        IO.pure(Try(serializer(bopt)).getOrElse(Map.empty))
    }

  private[config] def make[O <: BinanceOption]
  (parser: String => O, serializer: O => Map[String, String]): BinanceOptionT[O] =
    new BinanceOptionT[O] {

      override def parse(ref: String): IO[Option[O]] =
        IO.pure(Try(parser(ref)).toOption)

      override def serialize(bopt: O): IO[Map[String, String]] =
        IO.pure(Try(serializer(bopt)).getOrElse(Map.empty))
    }
}