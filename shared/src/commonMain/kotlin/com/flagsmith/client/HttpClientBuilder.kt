package com.flagsmith.client

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.headers
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

internal class HttpClientBuilder(
    private val clientFactory: HttpClientFactory,
) {
    fun build(
        baseUrl: String,
        environmentKey: String,
    ): HttpClient = clientFactory.build { config ->
        config.install(ContentNegotiation) {
            json(Json)
        }

        config.expectSuccess = true

        config.defaultRequest {
            url {
                host = baseUrl
            }

            headers {
                append("X-Environment-Key", environmentKey)
            }
        }
    }
}
