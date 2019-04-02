package io.github.brunogabriel.kotlinrest.controller

import io.github.brunogabriel.kotlinrest.model.Note
import io.github.brunogabriel.kotlinrest.repository.NoteRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

/**
 * Created by brunogabriel on 2019-03-31.
 */
@RestController
@RequestMapping("/api")
class NoteController(private val repository: NoteRepository) {

    @GetMapping("/notes")
    fun findAllNotes(): List<Note> = repository.findAll()

    @GetMapping("/notes/{id}")
    fun findNoteById(@PathVariable(value = "id") noteId: Long): ResponseEntity<Note> {
        return repository.findById(noteId).map {
            ResponseEntity.ok(it)
        }.orElse(ResponseEntity.notFound().build())
    }

    @PostMapping("/notes")
    fun createNote(@Valid @RequestBody note: Note): Note {
        return repository.save(note)
    }

    @PutMapping("/notes/{id}")
    fun updateNote(@PathVariable(value = "id") noteId: Long,
                   @Valid @RequestBody paramNote: Note): ResponseEntity<Note> {
        return repository.findById(noteId).map {
            val copiedNote = it.copy(title = paramNote.title, description = paramNote.description)
            ResponseEntity.ok().body(repository.save(copiedNote))
        }.orElse(ResponseEntity.notFound().build())
    }

    @DeleteMapping("/notes/{id}")
    fun deleteNoteById(@PathVariable(value = "id") noteId: Long): ResponseEntity<Void> {
        return repository.findById(noteId).map {
            repository.delete(it)
            ResponseEntity<Void>(HttpStatus.OK)
        }.orElse(ResponseEntity.notFound().build())
    }
}