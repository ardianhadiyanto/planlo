package io.hadiyanto.planlo.providers.plant

import com.fasterxml.jackson.annotation.JsonProperty
import io.hadiyanto.planlo.entities.Plant

class PlantInfoUrlList : MutableList<PlantInfoUrl> by ArrayList()

class PlantInfoUrl(val link: String?)

class PlantInfo(
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
