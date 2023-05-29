package io.github.brunogabriel.sampleapi.domain.service

import io.github.brunogabriel.sampleapi.data.repository.NoteRepository
import io.github.brunogabriel.sampleapi.infrastructure.mapper.CustomPageMapper
import io.github.brunogabriel.sampleapi.infrastructure.page.CustomPageModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import io.github.brunogabriel.sampleapi.domain.model.Note
import org.springframework.transaction.annotation.Transactional

interface NoteService {
    fun findAll(page: Pageable): CustomPageModel<Note>
    fun findById(id: Long): Note?
    fun save(note: Note): Note
    fun delete(note: Note)
    fun deleteById(id: Long)
    fun update(note: Note): Note
}

@Service
internal class NoteServiceImpl(
    @Autowired
    private val noteRepository: NoteRepository,
    @Autowired
    private val pageMapper: CustomPageMapper<Note>
) : NoteService {
    override fun findAll(page: Pageable): CustomPageModel<Note> =
        pageMapper.toModel(
            noteRepository.findAll(page)
        )

    override fun findById(id: Long): Note? = noteRepository.findById(id)
        .orElse(null)

    @Transactional
    override fun save(note: Note): Note = noteRepository.save(note)

    override fun delete(note: Note) = noteRepository.delete(note)

    override fun deleteById(id: Long) = noteRepository.deleteById(id)

    @Transactional
    override fun update(note: Note) = noteRepository.saveAndFlush(note)
}