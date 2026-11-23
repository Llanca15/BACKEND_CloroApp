package com.cloroapp.alertas.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {
    @Bean
    fun alertasOpenApi(): OpenAPI = OpenAPI()
        .info(
            Info()
                .title("CloroApp - ms-alertas API")
                .description("Microservicio de alertas y acciones correctivas ante desviaciones de cloro")
                .version("1.0.0")
        )
}
