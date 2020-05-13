package io.hadiyanto.planlo.entities

class Geolocation(val latitude: Double, val longitude: Double)

class Zipcode(val zip: String)

class MinTemperature(val temp: Double) {
  override fun toString() = temp.toString()
}

class Plant(
  val commonName: String,
  val scientificName: String,
  val duration: String
)
