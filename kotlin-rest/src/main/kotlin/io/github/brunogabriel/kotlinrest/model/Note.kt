package io.github.brunogabriel.kotlinrest.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.constraints.NotBlank

/**
 * Created by brunogabriel on 2019-03-31.
 */
@Entity
data class Note(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long = 0L,
        @get: NotBlank
        val title: String = "",
        @get: NotBlank
        val description: String = ""
)