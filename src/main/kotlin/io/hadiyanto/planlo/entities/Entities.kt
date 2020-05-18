package io.hadiyanto.planlo.entities

class Geolocation(val latitude: Double, val longitude: Double)

class Zipcode(val zip: String)

class TemperatureRange(val range: IntRange)

class Plant(
  val commonName: String,
  val scientificName: String,
  val minTemperatureInFahrenheit: Double,
  val duration: String
)
