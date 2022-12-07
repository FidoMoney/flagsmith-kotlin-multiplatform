package com.flagsmith.client

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.darwin.Darwin

actual class HttpClientFactoryImpl actual constructor() : HttpClientFactory {
    override fun build(config: (HttpClientConfig<*>) -> Unit): HttpClient {
        return HttpClient(Darwin) { config(this) }
    }
}
