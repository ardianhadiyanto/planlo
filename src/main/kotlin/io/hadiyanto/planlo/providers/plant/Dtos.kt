package io.hadiyanto.planlo.providers.plant

import com.fasterxml.jackson.annotation.JsonProperty
import io.hadiyanto.planlo.entities.Plant

class PlantInfoUrlsList : MutableList<PlantInfoUrl> by ArrayList()

data class PlantInfoUrl(val link: String?)

data class PlantInfo(
  @JsonProperty("common_name") val commonName: String?,
  @JsonProperty("scientific_name") val scientificName: String?,
  @JsonProperty("main_species") val mainSpecies: MainSpecies?,
  val duration: String?
) {
  fun toPlant(): Plant {
    return Plant(
      commonName ?: "",
      scientificName ?: "",
      mainSpecies?.growth?.temperature?.inFahrenheit ?: Double.NaN,
      duration ?: ""
    )
  }
}

class MainSpecies(val growth: Growth?)

class Growth(
  @JsonProperty("temperature_minimum") val temperature: Temperature?
)

class Temperature(
  @JsonProperty("deg_f") val inFahrenheit: Double?
)
