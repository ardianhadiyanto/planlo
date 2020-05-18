package io.hadiyanto.planlo.providers.temperature

import io.hadiyanto.planlo.entities.TemperatureRange
import io.hadiyanto.planlo.entities.Zipcode
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

interface TemperatureProvider {
  fun minTemperatureFor(zipcode: Zipcode): TemperatureRange
}

@Service
class PhzmApi(
  @Value("\${temperature-provider.url}") private val temperatureProviderUrl: String
) : TemperatureProvider {
  private val restTemplate = RestTemplate()

  override fun minTemperatureFor(zipcode: Zipcode): TemperatureRange {
    val url = "$temperatureProviderUrl/${zipcode.zip}.json"
    val response = restTemplate.getForEntity(url, HardinessZone::class.java)
    val hardinessZone = response.body
      ?: throw HardinessZoneNotFound()
    val intRange = hardinessZone.temperatureRange.takeIf { it != null }
      ?.let { it.toRange() }
      ?: throw UnknownTemperatureRange()

    return TemperatureRange(intRange)
  }

  private fun String.toRange() = this.split(" ").let { it[0].toInt()..it[2].toInt() }
}
