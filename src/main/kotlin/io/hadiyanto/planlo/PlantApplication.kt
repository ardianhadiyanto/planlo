package io.hadiyanto.planlo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PlantApplication

fun main(args: Array<String>) {
	runApplication<PlantApplication>(*args)
}
