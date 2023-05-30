package io.github.brunogabriel.sampleapi.infrastructure.handler

import io.github.resilience4j.ratelimiter.RequestNotPermitted
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import jakarta.servlet.http.HttpServletRequest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.springframework.http.HttpStatus


class RateLimiterHandlerTest {
    @MockK
    private lateinit var logger: Logger

    private lateinit var rateLimiterHandler: RateLimiterHandler

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        rateLimiterHandler = RateLimiterHandler(logger)
    }

    @Test
    fun `should handle exception`() {
        // given
        val exception = mockk<RequestNotPermitted>()
        val request = mockk<HttpServletRequest>()

        // when
        every { logger.warn(any<String>(), any(), any()) } just runs
        every { request.requestURI } returns "requestURI"
        every { exception.message } returns "message"

        // then
        val result = rateLimiterHandler.handleNotPermitted(exception, request)
        assertEquals(HttpStatus.TOO_MANY_REQUESTS, result.statusCode)
        assertEquals(TooManyRequestsModel(), result.body)
    }
}