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
    fun openAPI(): OpenAPI =
      OpenAPI()
        .info(
          Info()
            .title("Notes API")
            .description("This API allows you to perform CRUD operations on Notes.")
            .version("1.0.0")
        )

}