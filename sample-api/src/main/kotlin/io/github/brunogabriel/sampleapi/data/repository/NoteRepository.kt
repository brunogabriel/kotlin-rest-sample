package io.github.brunogabriel.sampleapi.data.repository

import io.github.brunogabriel.sampleapi.domain.model.Note
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface NoteRepository : JpaRepository<Note, Long>