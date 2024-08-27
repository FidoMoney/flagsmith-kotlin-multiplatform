package com.flagsmith.client

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.headers
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

internal class HttpClientBuilder(
    private val clientFactory: HttpClientFactory,
) {
    fun build(
        isHttps: Boolean,
        baseUrl: String,
        apiPath: String,
        apiVersion: String,
        environmentKey: String,
        logLevel: LogLevel,
    ): HttpClient = clientFactory.build { config ->
        config.install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    coerceInputValues = true
                }
            )
        }

        config.install(Logging) {
            this.logger = Logger.SIMPLE
            this.level = logLevel
        }

        config.expectSuccess = true

        config.defaultRequest {
            url(
                scheme = (URLProtocol.HTTPS.takeIf {
                    isHttps
                } ?: URLProtocol.HTTP).name,
                host = baseUrl,
                path = "/$apiPath/$apiVersion/"
            )

            contentType(ContentType.Application.Json)

            headers {
                append("X-Environment-Key", environmentKey)
            }
        }
    }
}
