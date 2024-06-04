package com.chris.marvel.data

import com.chris.api.MarvelSDKApi
import com.chris.api.data.Image
import com.chris.marvel.data.model.ComicEntity

interface ComicsRepository {
    suspend fun getComic(comicId: String): Result<ComicEntity>
}

class ComicsRepositoryImpl(private val marvelSDK: MarvelSDKApi) : ComicsRepository {
    override suspend fun getComic(comicId: String): Result<ComicEntity> {
        return marvelSDK.comicsResource.getComic(comicId).map {
            ComicEntity(
                it.title,
                it.description,
                it.thumbnail?.getUrl(Image.ImageVariants.PORTRAIT_UNCANNY)
            )
        }
    }
}
