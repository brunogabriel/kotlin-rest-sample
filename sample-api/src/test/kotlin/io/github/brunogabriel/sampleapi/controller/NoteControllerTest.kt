package io.github.brunogabriel.sampleapi.controller

import io.github.brunogabriel.sampleapi.domain.model.Note
import io.github.brunogabriel.sampleapi.domain.service.NoteService
import io.github.brunogabriel.sampleapi.infrastructure.page.CustomPageModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

class NoteControllerTest {

    @MockK
    private lateinit var service: NoteService

    private lateinit var noteController: NoteController

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        noteController = NoteController(service)
    }
    @Test
    fun `should create note`() {
        // given
        val fake = createFake()

        // when
        every { service.save(fake) } returns fake

        // then
        noteController.createNote(fake).apply {
            assertEquals(HttpStatus.CREATED, statusCode)
            assertEquals(fake, body)
        }
    }

    @Test
    fun `should not find by id`() {
        // when
        every { service.findById(any()) } returns null

        // then
        noteController.findById(10L).apply {
            assertEquals(HttpStatus.NOT_FOUND, statusCode)
        }
    }

    @Test
    fun `should find by id`() {
        // given
        val fake = createFake()

        every { service.findById(99L) } returns fake

        // then
        noteController.findById(99L).apply {
            assertEquals(HttpStatus.OK, statusCode)
            assertEquals(fake, body)
        }
    }

    @Test
    fun `should find all`() {
        // given
        val pageModel = mockk<CustomPageModel<Note>>()

        // when
        every { service.findAll(any()) } returns pageModel

        // then
        noteController.findAll(0,10).apply {
            assertEquals(HttpStatus.OK, statusCode)
            assertEquals(pageModel, body)
        }
    }

    @Test
    fun `should not update`() {
        // when
        every { service.update(any(), any()) } returns null

        // then
        noteController.update(10L, createFake()).apply {
            assertEquals(HttpStatus.NOT_FOUND, statusCode)
        }
    }

    @Test
    fun `should update`() {
        // given
        val fake = createFake()

        // when
        every { service.update(10L, fake) } returns fake

        // then
        noteController.update(10L, fake).apply {
            assertEquals(HttpStatus.OK, statusCode)
            assertEquals(fake, body)
        }
    }

    @Test
    fun `should delete`() {
        // when
        every { service.deleteById(any()) } just runs

        // then
        noteController.delete(10L).apply {
            assertEquals(HttpStatus.NO_CONTENT, statusCode)
        }
    }

    private fun createFake() = Note(id = 1L, title = "title", content = "content")
}