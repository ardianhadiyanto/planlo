package io.hadiyanto.planlo.controllers

import io.hadiyanto.planlo.entities.Geolocation
import io.hadiyanto.planlo.services.PlantsService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/plants")
class PlantsController(
  private val plantsService: PlantsService
) {
  @GetMapping
  fun getPlantsFor(geolocation: Geolocation) = plantsService.getPlantsFor(geolocation)
}
