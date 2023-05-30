package io.github.brunogabriel.sampleapi.infrastructure.handler

import io.github.resilience4j.ratelimiter.RequestNotPermitted
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
internal class RateLimiterHandler {
    private val logger: Logger = LoggerFactory.getLogger(RateLimiterHandler::class.java)

    @ExceptionHandler(RequestNotPermitted::class)
    fun handleNotPermitted(exception: RequestNotPermitted, request: HttpServletRequest): ResponseEntity<TooManyRequestsModel> =
      ResponseEntity(TooManyRequestsModel(), HttpStatus.TOO_MANY_REQUESTS).apply {
          logger.warn("Request to path '{}' is blocked due to rate-limiting. {}",
            request.requestURI, exception.message)
      }

}