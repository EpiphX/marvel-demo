package com.chris.api.data

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ImageTest {
    @Test
    fun `get url from image, expected to have proper path url requested`() {
        val image = Image(
            extension = "jpg",
            rawUrl = "http://i.annihil.us/u/prod/marvel/i/mg/c/30/52b4b7bb7a2e9"
        )
        val expected =
            "http://i.annihil.us/u/prod/marvel/i/mg/c/30/52b4b7bb7a2e9/portrait_uncanny.jpg"
        val actual = image.getUrl(Image.ImageVariants.PORTRAIT_UNCANNY)
        assertEquals(expected, actual)
    }
}