package com.mercadolivro.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfiguration {

    fun usesMicroserviceOpenAPI(): OpenAPI {
        return OpenAPI().info(Info()
            .title("Mercado Livro API")
            .description("API")
            .version("1.0")
        )
    }
}