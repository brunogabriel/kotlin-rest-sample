package io.github.brunogabriel.sampleapi.infrastructure.page

data class CustomPageModel<T>(
    val data: List<T>,
    val totalPages: Int = 0,
    val totalElements: Long = 0L,
    val currentPage: Int = 0,
    val next: Int = 0
)