package io.github.brunogabriel.sampleapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SampleApiApplication

fun main(args: Array<String>) {
	runApplication<SampleApiApplication>(*args)
}
