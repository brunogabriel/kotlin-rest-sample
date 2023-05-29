package io.github.brunogabriel.sampleapi.domain.service

import io.github.brunogabriel.sampleapi.data.repository.NoteRepository
import io.github.brunogabriel.sampleapi.domain.model.Note
import io.github.brunogabriel.sampleapi.infrastructure.mapper.CustomPageMapper
import io.github.brunogabriel.sampleapi.infrastructure.page.CustomPageModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import java.util.Optional

class NoteServiceTest {
    @MockK
    private lateinit var noteRepository: NoteRepository


    @MockK
    private lateinit var pageMapper: CustomPageMapper<Note>

    private lateinit var service: NoteService

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        service = NoteServiceImpl(noteRepository, pageMapper)
    }

    @Test
    fun `should findAll`() {
        // given
        val note = Note(1L, "title", "content")
        val page = mockk<Pageable>()
        val pageable = PageImpl(listOf(note))
        val customPageDTO = CustomPageModel(listOf(note))

        // when
        every { noteRepository.findAll(page) } returns pageable
        every { pageMapper.toModel(any()) } returns customPageDTO

        // then
        assertEquals(customPageDTO, service.findAll(page))
    }

    @Test
    fun `should save`() {
        // given
        val note = Note(1L, "title", "content")

        // when
        every { noteRepository.save(note) } returns note

        // when
        assertEquals(note, service.save(note))
    }

    @Test
    fun `should update`() {
        // given
        val note = Note(1L, "title", "content")

        // when
        every { noteRepository.saveAndFlush(note) } returns note

        // when
        assertEquals(note, service.update(note))
    }

    @Test
    fun `should find by id`() {
        // given
        val note = Note(1L, "title", "content")

        // when
        every { noteRepository.findById(1L) } returns Optional.of(note)

        // then
        assertEquals(note, service.findById(1L))
    }

    @Test
    fun `should not find by id`() {
        // when
        every { noteRepository.findById(1L) } returns Optional.empty()

        // then
        assertNull(service.findById(1L))
    }
}