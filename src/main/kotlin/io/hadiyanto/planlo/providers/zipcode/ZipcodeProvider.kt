package io.hadiyanto.planlo.providers.zipcode

import com.fasterxml.jackson.annotation.JsonProperty
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
          val reverseGeocode = response.body!!

          Zipcode(reverseGeocode.address!!.zipcode ?: "")
        } ?: throw RuntimeException("something went wrong")
    }
  }

  private fun urlFor(geolocation: Geolocation) = "$reverseGeocodeUrl?lat=${geolocation.latitude}&lon=${geolocation.longitude}&format=json"
}

private data class ReverseGeocode(val address: Address?)
private data class Address(
  @JsonProperty("postcode") val zipcode: String?
)
