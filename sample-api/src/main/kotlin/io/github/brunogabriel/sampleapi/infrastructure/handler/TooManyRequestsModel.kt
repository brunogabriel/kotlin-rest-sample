package io.github.brunogabriel.sampleapi.infrastructure.handler

data class TooManyRequestsModel(
  val cause: String = "Too many requests"
)