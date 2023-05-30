package io.github.brunogabriel.sampleapi.infrastructure.config

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SwaggerConfigurationTest {
    @Test
    fun `should OpenAPI`() {
        // then
        val result = SwaggerConfiguration().openAPI()
        assertEquals("Notes API", result.info.title)
        assertEquals("This API allows you to perform CRUD operations on Notes.", result.info.description)
        assertEquals("1.0.0", result.info.version)
    }
}