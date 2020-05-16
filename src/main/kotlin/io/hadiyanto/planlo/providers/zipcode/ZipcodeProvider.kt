package io.hadiyanto.planlo.providers.zipcode

import io.hadiyanto.planlo.entities.Geolocation
import io.hadiyanto.planlo.entities.Zipcode
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

interface ZipcodeProvider {
  fun zipcodeFor(geolocation: Geolocation): Zipcode
}

@Service
class OpenGeocodingApi(
  @Value("\${reverse-geocode-provider.url}") private val reverseGeocodeUrl: String
) : ZipcodeProvider {
  private val restTemplate = RestTemplate()

  override fun zipcodeFor(geolocation: Geolocation) = geolocation.lookupZipcode()

  private fun Geolocation.lookupZipcode(): Zipcode {
    return urlFor(this).let { url ->
      restTemplate.getForEntity(url, ReverseGeocode::class.java)
        .let { response ->
          val reverseGeocode = response.body
            ?: throw ZipcodeNotFound("Cannot find zipcode from lat=${this.latitude} long=${this.longitude}")
          val zipcode = reverseGeocode.address?.zipcode
            ?: throw ZipcodeNotFound("Cannot find zipcode from lat=${this.latitude} long=${this.longitude}")
          Zipcode(zipcode)
        }
    }
  }

  private fun urlFor(geolocation: Geolocation) = "$reverseGeocodeUrl?lat=${geolocation.latitude}&lon=${geolocation.longitude}&format=json"
}
