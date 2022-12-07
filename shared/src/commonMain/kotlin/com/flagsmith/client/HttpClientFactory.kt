package com.flagsmith.client

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig

internal interface HttpClientFactory {
    fun build(
        config: (HttpClientConfig<*>) -> Unit,
    ): HttpClient
}
