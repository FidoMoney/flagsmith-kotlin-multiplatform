package com.flagsmith.client

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig

expect class HttpClientFactoryImpl() : HttpClientFactory {
    override fun build(
        config: (HttpClientConfig<*>) -> Unit,
    ): HttpClient
}
