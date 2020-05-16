package io.hadiyanto.planlo.providers.temperature

import io.hadiyanto.planlo.entities.MinTemperature
import io.hadiyanto.planlo.entities.Zipcode
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

interface TemperatureProvider {
  fun minTemperatureFor(zipcode: Zipcode): MinTemperature
}

@Service
class PhzmApi(
  @Value("\${temperature-provider.url}") private val temperatureProviderUrl: String
) : TemperatureProvider {
  private val restTemplate = RestTemplate()

  override fun minTemperatureFor(zipcode: Zipcode): MinTemperature {
    val url = "$temperatureProviderUrl/${zipcode.zip}.json"
    val response = restTemplate.getForEntity(url, HardinessZone::class.java)
    val hardinessZone = response.body
      ?: throw HardinessZoneNotFound()
    val temperatureRange = hardinessZone.temperatureRange
      ?: throw UnknownTemperatureRange()

    return MinTemperature(temperatureRange.split(" ")[0].toDouble())
  }
}
