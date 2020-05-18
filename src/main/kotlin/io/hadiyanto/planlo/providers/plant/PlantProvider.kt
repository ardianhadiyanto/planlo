package io.hadiyanto.planlo.providers.plant

import io.hadiyanto.planlo.entities.TemperatureRange
import io.hadiyanto.planlo.entities.Plant
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

interface PlantProvider {
  fun plantsFor(tempRange: TemperatureRange): List<Plant>
}

@Service
class TreflePlantProvider(
  @Value("\${plants-provider.url}") private val plantsProviderUrl: String,
  @Value("\${plants-provider.token}") private val token: String
) : PlantProvider {
  private val restTemplate = RestTemplate()

  override fun plantsFor(tempRange: TemperatureRange): List<Plant> {
    return tempRange.urlsByTemperature()
      .map(::toPlantInfoUrlList)
      .filterNotNull()
      .flatMap { it.mapNotNull { toPlant("${it.link}?token=$token") } }
      .filterNot { it.minTemperatureInFahrenheit.isNaN() }
  }

  private fun TemperatureRange.urlsByTemperature(): List<String> {
    return this.range.map { "$plantsProviderUrl?token=$token&temperature_minimum_deg_f=$it" }
  }

  private fun toPlantInfoUrlList(plantUrl: String): PlantInfoUrlList? {
    val response = restTemplate.getForEntity(plantUrl, PlantInfoUrlList::class.java)
    val plantInfoUrlList = response.body
      ?: return null
    return plantInfoUrlList
  }

  private fun toPlant(url: String): Plant? {
    val httpsUrl = tohttpsUrl(url)
    val restTemplate = RestTemplate()
    val response = restTemplate.getForEntity(httpsUrl, PlantInfo::class.java)
    val plantInfo = response.body
      ?: return null
    return plantInfo.toPlant()
  }

  private fun tohttpsUrl(url: String) = url.split(":").let {
    "https:${it[1]}"
  }
}
