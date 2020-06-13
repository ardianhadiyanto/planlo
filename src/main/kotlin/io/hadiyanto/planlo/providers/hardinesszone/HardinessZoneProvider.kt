package io.hadiyanto.planlo.providers.hardinesszone

import io.hadiyanto.planlo.entities.TemperatureRange
import io.hadiyanto.planlo.entities.Zipcode
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

interface HardinessZoneProvider {
  fun hardinessZoneFor(zipcode: Zipcode): TemperatureRange?
}

@Service
class PhzmApi(
  @Value("\${hardiness-zone-provider.url}") private val temperatureProviderUrl: String
) : HardinessZoneProvider {
  override fun hardinessZoneFor(zipcode: Zipcode): TemperatureRange? {
    return RestTemplate().getForEntity("$temperatureProviderUrl/${zipcode.zip}.json", HardinessZone::class.java)
      .let { response -> response.body?.temperatureRange?.toTemperatureRange() }
  }

  private fun String.toTemperatureRange() = this.split(" ").let { TemperatureRange(it[0].toInt()..it[2].toInt()) }
}
