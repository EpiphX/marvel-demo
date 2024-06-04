package com.chris.api

import com.chris.api.internal.api.Client
import com.chris.api.resources.ComicsResource
import com.chris.api.resources.ComicsResourceImpl
import io.ktor.client.HttpClient

/**
 * The [MarvelSDKApi] is the main way of accessing resources.
 * The library encapsulates the transformation of the private and public api keys via a MD5 digest.
 * And also exposes easier to consume models vs the optionality of the direct api responses.
 */
interface MarvelSDKApi {
    /**
     * Comics resource refers to all endpoints that fall under the `/comics` path.
     */
    val comicsResource: ComicsResource
}

class MarvelSDK private constructor(privateApiKey: String, apiKey: String) : MarvelSDKApi {
    private val httpClient: HttpClient =
        Client().getHttpClient("gateway.marvel.com", privateApiKey, apiKey)

    /**
     * Resources
     */
    override val comicsResource: ComicsResource = ComicsResourceImpl(httpClient)

    companion object {
        /**
         * Initializes the [MarvelSDK] with an [apiKey].
         * This library requires an [privateApiKey] from [https://developer.marvel.com](https://developer.marvel.com)
         * This library requires an [apiKey] from [https://developer.marvel.com](https://developer.marvel.com)
         *
         * We're using the server side authentication method since this is a demo project, but if this lib was to be used in production,
         * we'd probably look to see if there's some way we can avoid baking in the private key.
         */
        fun initialize(privateApiKey: String, apiKey: String): MarvelSDK {
            return MarvelSDK(privateApiKey, apiKey)
        }
    }
}

