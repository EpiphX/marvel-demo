package com.chris.api

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class MarvelSDKTest {
    @Test
    fun `initialize sdk and test successful get comic`() {
        // It is possible to run an integration test of the SDK, by just replacing these with your private / pub keys.
        // TODO: Look into a way that this can also be pulled from the local.properties.
        val marvelSDK = MarvelSDK.initialize(
            privateApiKey = "{Your Private Key}",
            apiKey = "{Your Public Key}"
        )

        runBlocking {
            val comic = marvelSDK.comicsResource.getComic("47800")
            Assertions.assertTrue(comic.isSuccess)
            Assertions.assertNotNull(comic.getOrNull())
        }
    }
}