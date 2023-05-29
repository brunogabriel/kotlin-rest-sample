package io.github.brunogabriel.sampleapi.domain.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Entity
data class Note(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @field:NotBlank(message = "Title must not be blank")
    @field:Size(max = 100, message = "Title must not exceed 100 characters")
    val title: String = "",

    @field:NotBlank(message = "Content must not be blank")
    @field:Size(max = 1000, message = "Content must not exceed 1000 characters")
    val content: String = ""
)