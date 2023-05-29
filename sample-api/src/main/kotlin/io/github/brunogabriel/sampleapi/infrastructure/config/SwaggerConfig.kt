package io.github.brunogabriel.sampleapi.infrastructure.config

import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.models.OpenAPI
import org.springframework.context.annotation.Configuration
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean


@Configuration
@Tag(name = "Notes API", description = "Endpoints Notes API")
internal class SwaggerConfiguration {

    @Bean
    fun openAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("Notes API")
                    .version("1.0.0")
            )

    }
}