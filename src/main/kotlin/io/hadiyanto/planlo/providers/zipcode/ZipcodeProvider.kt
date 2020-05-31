package io.hadiyanto.planlo.providers.zipcode

import io.hadiyanto.planlo.entities.Geolocation
import io.hadiyanto.planlo.entities.Zipcode
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

interface ZipcodeProvider {
  fun zipcodeFor(geolocation: Geolocation): Zipcode?
}

@Service
class OpenGeocodingApi(
  @Value("\${reverse-geocode-provider.url}") private val reverseGeocodeUrl: String
) : ZipcodeProvider {
  private val restTemplate = RestTemplate()

  override fun zipcodeFor(geolocation: Geolocation): Zipcode? {
    return restTemplate.getForEntity(urlFor(geolocation), ReverseGeocode::class.java)
      .let { response -> response.body?.address?.zipcode?.toZipcode() }
  }

  private fun urlFor(geolocation: Geolocation) = "$reverseGeocodeUrl?lat=${geolocation.latitude}&lon=${geolocation.longitude}&format=json"

  private fun String.toZipcode() = Zipcode(this)
}
