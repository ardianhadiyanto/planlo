package io.hadiyanto.planlo.providers.plant

import com.fasterxml.jackson.annotation.JsonProperty
import io.hadiyanto.planlo.entities.MinTemperature
import io.hadiyanto.planlo.entities.Plant
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

interface PlantProvider {
  fun plantsFor(minTemp: MinTemperature): List<Plant>
}

private class PlantInfo(
  @JsonProperty("common_name") val commonName: String?,
  @JsonProperty("scientific_name") val scientificName: String?,
  val duration: String?
) {
  fun toPlant(): Plant {
    return Plant(
      commonName ?: "",
      scientificName ?: "",
      duration ?: ""
    )
  }
}

private class PlantInfoUrl(val link: String?)
private class PlantInfoUrlList : MutableList<PlantInfoUrl> by ArrayList()

@Service
class TreflePlantProvider(
  @Value("\${plants-provider.url}") private val url: String,
  @Value("\${plants-provider.token}") private val token: String
) : PlantProvider {

  override fun plantsFor(minTemp: MinTemperature): List<Plant> {
    val plantUrl = "$url?token=$token&temperature_minimum_deg_f=$minTemp"
    val restTemplate = RestTemplate()
    val response = restTemplate.getForEntity(plantUrl, PlantInfoUrlList::class.java)
    val plantInfoUrlList = response.body
      ?: throw RuntimeException("Oops")

    return plantInfoUrlList.map { plantInfo("${it.link!!}?token=$token") }
  }

  private fun plantInfo(url: String): Plant {
    val theRealUrl = url.split(":").let {
      "https:${it[1]}"
    }
    val restTemplate = RestTemplate()
    val response = restTemplate.getForEntity(theRealUrl, PlantInfo::class.java)
    val plantInfo = response.body
      ?: throw RuntimeException("Oops")
    return plantInfo.toPlant()
  }
}
