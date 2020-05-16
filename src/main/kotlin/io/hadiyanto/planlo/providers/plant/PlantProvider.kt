package io.hadiyanto.planlo.providers.plant

import io.hadiyanto.planlo.entities.MinTemperature
import io.hadiyanto.planlo.entities.Plant
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

interface PlantProvider {
  fun plantsFor(minTemp: MinTemperature): List<Plant>
}

@Service
class TreflePlantProvider(
  @Value("\${plants-provider.url}") private val plantsProviderUrl: String,
  @Value("\${plants-provider.token}") private val token: String
) : PlantProvider {

  override fun plantsFor(minTemp: MinTemperature): List<Plant> {
    val plantUrl = "$plantsProviderUrl?token=$token&temperature_minimum_deg_f=$minTemp"
    val restTemplate = RestTemplate()
    val response = restTemplate.getForEntity(plantUrl, PlantInfoUrlList::class.java)
    val plantInfoUrlList = response.body
      ?: throw UnknownPlantsException()
    val plantInfoUrlLists = plantInfoUrlList.filter { it.link == null }

    return plantInfoUrlList.filterNotNull()
      .map { plantInfo("${it.link}?token=$token") }
      .mapNotNull { it }
  }

  private fun plantInfo(url: String): Plant? {
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
