package io.hadiyanto.planlo.providers.temperature

import com.fasterxml.jackson.annotation.JsonProperty
import io.hadiyanto.planlo.entities.MinTemperature
import io.hadiyanto.planlo.entities.Zipcode
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import kotlin.String as String

interface TemperatureProvider {
  fun minTemperatureFor(zipcode: Zipcode): MinTemperature
}

data class HardinessZone(
  val zone: String?,
  val coordinates: Coordinates?,
  @JsonProperty("temperature_range") val temperatureRange: String?
)

data class Coordinates(
  val lat: Double?,
  val lon: Double?
)

@Service
class PhzmApi(
  @Value("\${temperature-provider.url}") private val url: String
) : TemperatureProvider {
  private val restTemplate = RestTemplate()

  override fun minTemperatureFor(zipcode: Zipcode): MinTemperature {
    val temperatureUrl = "$url/${zipcode.zip}.json"
    val response = restTemplate.getForEntity(temperatureUrl, HardinessZone::class.java)
    val hardinessZone = response.body
      ?: throw RuntimeException("Oops")

    return MinTemperature(hardinessZone.temperatureRange!!.split(" ")[0].toDouble())
  }
}
