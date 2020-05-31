package io.hadiyanto.planlo.providers.temperature

import io.hadiyanto.planlo.entities.TemperatureRange
import io.hadiyanto.planlo.entities.Zipcode
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

interface TemperatureProvider {
  fun minTemperatureFor(zipcode: Zipcode): TemperatureRange?
}

@Service
class PhzmApi(
  @Value("\${temperature-provider.url}") private val temperatureProviderUrl: String
) : TemperatureProvider {
  private val restTemplate = RestTemplate()

  override fun minTemperatureFor(zipcode: Zipcode): TemperatureRange? {
    return restTemplate.getForEntity("$temperatureProviderUrl/${zipcode.zip}.json", HardinessZone::class.java)
      .let { response -> response.body?.temperatureRange?.toTemperatureRange() }
  }

  private fun String.toTemperatureRange() = this.split(" ").let { TemperatureRange(it[0].toInt()..it[2].toInt()) }
}
