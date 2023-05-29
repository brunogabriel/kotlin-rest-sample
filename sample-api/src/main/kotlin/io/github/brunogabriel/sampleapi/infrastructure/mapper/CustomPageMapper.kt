package io.github.brunogabriel.sampleapi.infrastructure.mapper

import io.github.brunogabriel.sampleapi.infrastructure.page.CustomPageModel
import org.springframework.data.domain.Page
import org.springframework.stereotype.Component

@Component
internal class CustomPageMapper<T> {
    fun toModel(page: Page<T>): CustomPageModel<T> {
        return CustomPageModel(
            data = page.content,
            totalPages = page.totalPages,
            totalElements = page.totalElements,
            currentPage = page.number,
            next = page.size
        )
    }
}