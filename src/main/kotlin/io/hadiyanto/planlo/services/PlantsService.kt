package io.hadiyanto.planlo.services

import io.hadiyanto.planlo.entities.Geolocation
import io.hadiyanto.planlo.entities.Plant
import io.hadiyanto.planlo.providers.plant.PlantProvider
import io.hadiyanto.planlo.providers.temperature.TemperatureProvider
import io.hadiyanto.planlo.providers.zipcode.ZipcodeProvider
import org.springframework.stereotype.Service

@Service
class PlantsService(
  private val zipcodeProvider: ZipcodeProvider,
  private val temperatureProvider: TemperatureProvider,
  private val plantProvider: PlantProvider
) {
  fun getPlantsFor(geolocation: Geolocation): List<Plant> {
    val zipcode = zipcodeProvider.zipcodeFor(geolocation)
    val minTemp = temperatureProvider.minTemperatureFor(zipcode)
    return plantProvider.plantsFor(minTemp)
  }
}
