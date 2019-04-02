package io.github.brunogabriel.kotlinrest.controller

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.github.brunogabriel.kotlinrest.model.Note
import io.github.brunogabriel.kotlinrest.repository.NoteRepository
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.util.*

/**
 * Created by brunogabriel on 2019-04-01.
 */
class NoteControllerTest {
    private lateinit var controller: NoteController
    private lateinit var repository: NoteRepository

    @Before
    fun setUp() {
        repository = mock()
        controller = NoteController(repository)
    }

    @Test
    fun shouldReturnNotesWhenFindAllNotes() {
        // given
        val notes = createNotes()
        whenever(repository.findAll()).thenReturn(notes)

        // when
        val result = controller.findAllNotes()

        // then
        assertEquals(notes, result)
    }

    @Test
    fun shouldReturnNoteByIdWhenFindNoteById() {
        // given
        val notes = createNotes()
        whenever(repository.findById(144L)).thenReturn(Optional.of(notes[1]))

        // when
        val result = controller.findNoteById(144L)

        // then
        assertEquals(ResponseEntity.ok(notes[1]), result)
    }

    @Test
    fun shouldNotReturnNoteWhenFindNoteById() {
        // given
        whenever(repository.findById(144L)).thenReturn(Optional.ofNullable(null))

        // then
        assertEquals(ResponseEntity.notFound().build<Note>(), controller.findNoteById(144L))
    }

    @Test
    fun shouldCreateNote() {
        // given
        val note = Note(1L, "title", "description")
        whenever(repository.save(note)).thenReturn(note)

        // when
        val result = controller.createNote(note)

        // then
        assertEquals(note, result)
    }

    @Test
    fun shouldNotUpdateNoteWhenNoteNotFound() {
        // given
        val updatedNote = Note(1L, "any", "any")
        whenever(repository.findById(10L)).thenReturn(Optional.ofNullable(null))

        // then
        assertEquals(ResponseEntity.notFound().build<Note>(), controller.updateNote(1L, updatedNote))
    }

    @Test
    fun shouldUpdateNote() {
        // given
        val updatedNote = Note(1L, "anyTitle", "anyDescription")
        val repositoryNote = Note(1L, "repository", "repository")
        whenever(repository.findById(1L)).thenReturn(Optional.of(repositoryNote))
        whenever(repository.save(updatedNote)).thenReturn(updatedNote)

        // when
        val result = controller.updateNote(1L, updatedNote)

        // then
        verify(repository, times(1)).save(updatedNote)
        assertEquals(ResponseEntity.ok(updatedNote), result)
    }

    @Test
    fun shouldNoteDeleteNoteById() {
        // given
        whenever(repository.findById(133L)).thenReturn(Optional.ofNullable(null))

        // then
        assertEquals(ResponseEntity.notFound().build<Note>(), controller.deleteNoteById(133L))
    }

    @Test
    fun shouldDeleteNoteById() {
        // given
        val note = Note(133L, "anyNote", "anyDescription")
        whenever(repository.findById(133L)).thenReturn(Optional.ofNullable(note))

        // when
        val result = controller.deleteNoteById(133L)

        // then
        verify(repository, times(1)).delete(note)
        assertEquals(ResponseEntity<Void>(HttpStatus.OK), result)
    }

    private fun createNotes() = listOf(
            Note(13L, "title13", "description13"),
            Note(144L, "title144", "description144"),
            Note(199L, "title199", "description199"))
}