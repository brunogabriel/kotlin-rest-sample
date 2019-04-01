package io.github.brunogabriel.kotlinrest.repository

import io.github.brunogabriel.kotlinrest.model.Note
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Created by brunogabriel on 2019-03-31.
 */

@Repository
interface NoteRepository : JpaRepository<Note, Long>