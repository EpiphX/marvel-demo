package com.chris.api.resources

import com.chris.api.data.Comic
import com.chris.api.data.Image
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.serialization.Serializable

/**
 * The Comics Resource supports endpoints for the marvel comics resource definition:
 *
 * Currently supports the following endpoints:
 * [Fetch Comic](https://developer.marvel.com/docs#!/public/getComicIndividual_get_7)
 */
interface ComicsResource {
    /**
     * Retrieves a comic
     */
    suspend fun getComic(comicId: String): Result<Comic>
}

class ComicsResourceImpl(private val httpClient: HttpClient) : ComicsResource {
    @Serializable
    internal data class ComicDTO(
        val data: DataDTO?
    ) {
        @Serializable
        internal data class DataDTO(
            val offset: Int?,
            val limit: Int?,
            val total: Int?,
            val count: Int?,
            val results: List<ComicResultsDTO>?
        )

        @Serializable
        internal data class ComicResultsDTO(
            val title: String?,
            val description: String?,
            val textObjects: List<TextObjects>?,
            val thumbnail: ImageObject?
        )

        @Serializable
        internal data class TextObjects(
            val type: String?,
            val text: String?
        )

        @Serializable
        internal data class ImageObject(
            val path: String?,
            val extension: String?
        )
    }

    override suspend fun getComic(comicId: String): Result<Comic> = Result.runCatching {
        val response: ComicDTO = httpClient.get("comics/${comicId}").body()
        val firstComicResult = response.data?.results?.firstOrNull()

        val image = firstComicResult?.thumbnail?.let {
            val extension = requireNotNull(it.extension) { "Image extension cannot be null." }
            // For some reason, all images are returned from the public api as http instead of https.
            // Rewriting this to https as it doesn't appear to have issues and allows for us to keep the url access to secured urls only.
            val path =
                requireNotNull(it.path?.replace("http", "https")) { "Image path cannot be null" }
            Image(extension, path)
        }

        val description = firstComicResult?.description?.takeIf { it.isNotEmpty() }
            ?: firstComicResult?.textObjects?.firstOrNull()?.text
            ?: "No comic description found."

        Comic(
            id = comicId,
            title = requireNotNull(firstComicResult?.title) { "title should not be null." },
            description = description,
            thumbnail = image
        )
    }
}