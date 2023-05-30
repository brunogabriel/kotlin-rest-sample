package io.github.brunogabriel.sampleapi.controller

import io.github.brunogabriel.sampleapi.domain.model.Note
import io.github.brunogabriel.sampleapi.domain.service.NoteService
import io.github.brunogabriel.sampleapi.infrastructure.page.CustomPageModel
import io.github.resilience4j.ratelimiter.annotation.RateLimiter
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestParam

@RestController
@RequestMapping(value = ["/api/v1/notes"], produces = [MediaType.APPLICATION_JSON_VALUE])
@Tag(name = "Notes")
internal class NoteController(
    @Autowired
    private val service: NoteService
) {
    @Operation(summary = "Create Notes")
    @PostMapping
    fun createNote(@Validated @RequestBody dto: Note): ResponseEntity<Note> =
        ResponseEntity(service.save(dto), HttpStatus.CREATED)

    @Operation(summary = "Get Note by id")
    @GetMapping("/{id}")
    fun findById(@PathVariable("id") id: Long): ResponseEntity<Note> =
        service.findById(id).let { note ->
            if (note != null) {
                ResponseEntity.ok(note)
            } else {
                ResponseEntity.notFound().build()
            }
        }

    @Operation(summary = "Get Notes by paging")
    @RateLimiter(name = "default")
    @GetMapping
    fun findAll(
        @Parameter(required = false) @RequestParam(defaultValue = "0") page: Int,
        @Parameter(required = false) @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<CustomPageModel<Note>> =
        ResponseEntity.ok(service.findAll(PageRequest.of(page, size)))

    @Operation(summary = "Update Note by id")
    @PutMapping("/{id}")
    fun update(@PathVariable("id") id: Long, @Validated @RequestBody note: Note): ResponseEntity<Note> =
        service.update(id, note).let { result ->
            if (result != null) {
                ResponseEntity.ok(result)
            } else {
                ResponseEntity.notFound().build()
            }
        }

    @Operation(summary = "Delete Note by id")
    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: Long): ResponseEntity<Any> = service.deleteById(id).let {
        ResponseEntity.noContent().build()
    }
}