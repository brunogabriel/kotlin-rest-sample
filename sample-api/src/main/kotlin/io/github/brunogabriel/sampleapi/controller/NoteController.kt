package io.github.brunogabriel.sampleapi.controller

import io.github.brunogabriel.sampleapi.domain.model.Note
import io.github.brunogabriel.sampleapi.domain.service.NoteService
import io.github.brunogabriel.sampleapi.infrastructure.page.CustomPageModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@RestController
@RequestMapping(value = ["/api/v1/notes"], produces = [MediaType.APPLICATION_JSON_VALUE])
internal class NoteController(
    @Autowired
    private val service: NoteService
) {
    @PostMapping
    fun createNote(@Validated @RequestBody dto: Note): ResponseEntity<Note> =
        ResponseEntity(service.save(dto), HttpStatus.CREATED)

    @GetMapping
    fun findAll(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<CustomPageModel<Note>> =
        ResponseEntity.ok(service.findAll(PageRequest.of(page, size)))
}