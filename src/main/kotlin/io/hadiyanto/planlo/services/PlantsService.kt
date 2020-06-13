package io.hadiyanto.planlo.services

import io.hadiyanto.planlo.entities.Plant
import io.hadiyanto.planlo.entities.Zipcode
import io.hadiyanto.planlo.providers.hardinesszone.HardinessZoneProvider
import io.hadiyanto.planlo.providers.plant.PlantProvider
import org.springframework.stereotype.Service

@Service
class PlantsService(
  private val hardinessZoneProvider: HardinessZoneProvider,
  private val plantProvider: PlantProvider
) {
  fun getPlants(zipcode: Zipcode): List<Plant> {
    val minTemp = hardinessZoneProvider.hardinessZoneFor(zipcode)
      ?: return emptyList()

    return plantProvider.plantsFor(minTemp)
  }
}
