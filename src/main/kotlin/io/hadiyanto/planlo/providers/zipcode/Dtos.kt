package io.hadiyanto.planlo.providers.zipcode

import com.fasterxml.jackson.annotation.JsonProperty

data class ReverseGeocode(val address: Address?)

data class Address(
  @JsonProperty("postcode") val zipcode: String?
)
