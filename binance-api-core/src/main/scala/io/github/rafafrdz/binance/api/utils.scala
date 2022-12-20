package io.github.rafafrdz.binance.api

import java.text.SimpleDateFormat
import java.util.Date
import scala.util.matching.Regex

object utils {

  object timestamp {
    def parse(date: String): NormalizedDate = NormalizedDate(date)

    case class NormalizedDate private(normalized: String, pattern: String) {
      def getDate: Date = new SimpleDateFormat(pattern).parse(normalized)

      def getTime: Long = getDate.getTime
    }

    object NormalizedDate {
      def apply(date: String): NormalizedDate =
        NormalizedDate(internal.normalizedDateFunc(date), internal.defaultPattern)
    }

    object internal {

      val defaultPattern: String = "yyyy/MM/dd HH:mm:ss"

      /** Regex */
      val dateR1: Regex = "([\\d]{4})[-/]([\\d]{2})[-/]([\\d]{2})".r
      val dateR2: Regex = "([\\d]{2})[-/]([\\d]{2})[-/]([\\d]{4})".r
      val timestampR1: Regex = "([\\d]{4})[-/]([\\d]{2})[-/]([\\d]{2})[T ]([\\d]{2}):([\\d]{2}):([\\d]{2})".r
      val timestampR2: Regex = "([\\d]{2})[-/]([\\d]{2})[-/]([\\d]{4})[T ]([\\d]{2}):([\\d]{2}):([\\d]{2})".r

      def normalizeDate(year: String, month: String, day: String, hour: String = "00", minutes: String = "00", seconds: String = "00")
      = s"$year/$month/$day $hour:$minutes:$seconds"

      val normalizedDateFunc: String => String = {
        case timestampR1(year, month, day, hour, minutes, seconds) => normalizeDate(year, month, day, hour, minutes, seconds)
        case timestampR2(day, month, year, hour, minutes, seconds) => normalizeDate(year, month, day, hour, minutes, seconds)
        case dateR1(year, month, day) => normalizeDate(year, month, day)
        case dateR2(day, month, year) => normalizeDate(year, month, day)
      }
    }
  }

}
