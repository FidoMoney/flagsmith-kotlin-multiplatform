package com.flagsmith.client

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.okhttp.OkHttp

actual class HttpClientFactoryImpl actual constructor() : HttpClientFactory {
    actual override fun build(config: (HttpClientConfig<*>) -> Unit): HttpClient {
        return HttpClient(OkHttp) { config(this) }
    }
}
