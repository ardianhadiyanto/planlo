package io.hadiyanto.planlo.providers.plant

import io.hadiyanto.planlo.entities.Plant
import io.hadiyanto.planlo.entities.TemperatureRange
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

interface PlantProvider {
  fun plantsFor(tempRange: TemperatureRange): List<Plant>
}

@Service
class Trefle(
  @Value("\${plants-provider.url}") private val plantsProviderUrl: String,
  @Value("\${plants-provider.token}") private val token: String
) : PlantProvider {
  override fun plantsFor(tempRange: TemperatureRange): List<Plant> {
    return runBlocking {
      val deferredPlantInfoUrlsList = tempRange.urlsByTemperature()
        .map { plantInfoUrl -> async { toPlantInfo(plantInfoUrl) } }

      val plantInfoUrlsList = deferredPlantInfoUrlsList.mapNotNull { deferredPlantInfoUrls -> deferredPlantInfoUrls.await() }

      val deferredPlants = plantInfoUrlsList.flatMap { plantInfoUrls ->
        plantInfoUrls.mapNotNull { plantInfoUrl ->
          async {
            toPlant("${plantInfoUrl.link}?token=$token")
          }
        }
      }

      deferredPlants.mapNotNull { deferredPlant -> deferredPlant.await() }
        .filterNot { plant -> plant.minTemperatureInFahrenheit.isNaN() }
    }
  }

  private fun TemperatureRange.urlsByTemperature(): List<String> {
    return this.range.map { "$plantsProviderUrl?token=$token&temperature_minimum_deg_f=$it" }
  }

  private fun toPlantInfo(plantUrl: String): PlantInfoUrlsList? {
    val response = RestTemplate().getForEntity(plantUrl, PlantInfoUrlsList::class.java)
    val plantInfoUrlList = response.body
      ?: return null
    return plantInfoUrlList
  }

  private fun toPlant(url: String): Plant? {
    val httpsUrl = tohttpsUrl(url)
    val response = RestTemplate().getForEntity(httpsUrl, PlantInfo::class.java)
    if (response.statusCode == HttpStatus.INTERNAL_SERVER_ERROR) {
      println("500")
    }
    val plantInfo = response.body
      ?: return null
    return plantInfo.toPlant()
  }

  private fun tohttpsUrl(url: String) = url.split(":").let {
    "https:${it[1]}"
  }
}
