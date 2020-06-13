package io.hadiyanto.planlo.providers.hardinesszone

class HardinessZoneNotFound(msg: String = "Cannot find hardiness zone"): RuntimeException(msg)

class UnknownTemperatureRange(msg: String = "Unknown temperature for the given area"): RuntimeException(msg)
