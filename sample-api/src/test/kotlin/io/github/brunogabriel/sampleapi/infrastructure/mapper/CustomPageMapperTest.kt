package io.github.brunogabriel.sampleapi.infrastructure.mapper

import io.github.brunogabriel.sampleapi.infrastructure.page.CustomPageModel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.data.domain.Page

class CustomPageMapperTest {
    private lateinit var mapper: CustomPageMapper<String>

    @BeforeEach
    fun setUp() {
        mapper = CustomPageMapper()
    }

    @Test
    fun `should map to page`() {
        assertEquals(
            CustomPageModel(
                data = emptyList<String>(),
                totalPages = 1,
                totalElements = 0,
                currentPage = 0,
                next = 0
            ), mapper.toModel(Page.empty())
        )
    }
}