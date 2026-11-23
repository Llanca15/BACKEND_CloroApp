package com.cloroapp.monitoreo.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {
    @Bean
    fun monitoreoOpenApi(): OpenAPI = OpenAPI()
        .info(
            Info()
                .title("CloroApp - ms-monitoreo API")
                .description("Microservicio de gestión de puntos de medición y registro de mediciones de cloro (ppm)")
                .version("1.0.0")
        )
}
