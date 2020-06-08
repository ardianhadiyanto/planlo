package io.hadiyanto.planlo.services

import io.hadiyanto.planlo.entities.Plant
import io.hadiyanto.planlo.entities.Zipcode
import io.hadiyanto.planlo.providers.plant.PlantProvider
import io.hadiyanto.planlo.providers.temperature.TemperatureProvider
import org.springframework.stereotype.Service

@Service
class PlantsService(
  private val temperatureProvider: TemperatureProvider,
  private val plantProvider: PlantProvider
) {
  fun getPlants(zipcode: Zipcode): List<Plant> {
    val minTemp = temperatureProvider.minTemperatureFor(zipcode)
      ?: return emptyList()

    return plantProvider.plantsFor(minTemp)
  }
}
