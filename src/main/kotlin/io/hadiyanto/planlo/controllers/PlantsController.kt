package io.hadiyanto.planlo.controllers

import io.hadiyanto.planlo.entities.Geolocation
import io.hadiyanto.planlo.entities.Plant
import io.hadiyanto.planlo.services.PlantsService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/plants")
class PlantsController(
  private val plantsService: PlantsService
) {
  @GetMapping
  fun getPlantsFor(geolocation: Geolocation): ResponseEntity<List<Plant>> {
    return try {
      ResponseEntity(plantsService.getPlantsFor(geolocation), HttpStatus.OK)
    } catch (e: RuntimeException) {
      return ResponseEntity(emptyList(), HttpStatus.INTERNAL_SERVER_ERROR)
    }
  }
}
