package io.hadiyanto.planlo.providers.hardinesszone

import com.fasterxml.jackson.annotation.JsonProperty

data class HardinessZone(
  val zone: String?,
  val coordinates: Coordinates?,
  @JsonProperty("temperature_range") val temperatureRange: String?
)

data class Coordinates(
  val lat: Double?,
  val lon: Double?
)
