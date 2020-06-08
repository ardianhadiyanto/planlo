package io.hadiyanto.planlo.entities

class Zipcode(val zip: String) {
  operator fun invoke() = zip
}

class TemperatureRange(val range: IntRange) {
  operator fun invoke() = range
}

class Plant(
  val commonName: String,
  val scientificName: String,
  val minTemperatureInFahrenheit: Double,
  val duration: String
)
