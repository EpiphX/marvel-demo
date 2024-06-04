package com.chris.api.internal.api

import com.chris.api.internal.security.MD5DigestFactory
import com.chris.api.internal.security.MD5DigestFactoryImpl
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ParametersBuilder
import io.ktor.http.URLProtocol
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

class Client {
    private val md5DigestFactory: MD5DigestFactory = MD5DigestFactoryImpl()

    @OptIn(ExperimentalSerializationApi::class)
    fun getHttpClient(baseUrl: String, privateApiKey: String, apiKey: String): HttpClient {
        return HttpClient(CIO) {
            defaultRequest {
                url {
                    val timeStamp = System.currentTimeMillis().toString()
                    val md5DigestInfo = md5DigestFactory.create(timeStamp, privateApiKey, apiKey)
                    protocol = URLProtocol.HTTPS
                    host = baseUrl
                    path("v1/public/")
                    md5DigestInfo.toParameters(parameters)
                }
            }

            // Installs JSON as content negotiation with this HttpClient.
            install(ContentNegotiation) {
                json(
                    Json {
                        encodeDefaults = true
                        isLenient = true
                        allowSpecialFloatingPointValues = true
                        allowStructuredMapKeys = true
                        prettyPrint = false
                        useArrayPolymorphism = false
                        ignoreUnknownKeys = true
                        explicitNulls = false
                    }
                )
            }
            // We'll default to 10s timeout for now, but this can be configured.
            install(HttpTimeout) {
                requestTimeoutMillis = 10000
            }
            install(Logging)
        }
    }
}

fun MD5DigestFactory.MD5DigestInfo.toParameters(parameters: ParametersBuilder) {
    parameters.append("ts", this.ts)
    parameters.append("apikey", this.apiKey)
    parameters.append("hash", this.hash)
}