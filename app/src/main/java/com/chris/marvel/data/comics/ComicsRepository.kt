package com.chris.marvel.data.comics

import com.chris.api.MarvelSDKApi
import com.chris.api.data.Comic
import com.chris.api.data.Image
import com.chris.marvel.data.comics.model.ComicData
import com.chris.marvel.data.storage.AppDatabase
import com.chris.marvel.data.storage.entities.ComicDBEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface ComicsRepository {
    suspend fun getComic(comicId: String): Result<ComicData>
}

class ComicsRepositoryImpl(
    private val marvelSDK: MarvelSDKApi,
    private val appDatabase: AppDatabase
) : ComicsRepository {
    override suspend fun getComic(comicId: String): Result<ComicData> =
        withContext(Dispatchers.IO) {
            val comicFromStorage = runCatching {
                appDatabase.comicsDao().getById(comicId)
            }.getOrNull()

            if (comicFromStorage != null) {
                Result.success(
                    ComicData(
                        id = comicFromStorage.id,
                        title = comicFromStorage.title,
                        description = comicFromStorage.description,
                        thumbnailUrl = comicFromStorage.thumbnailUrl
                    )
                )
            } else {
                marvelSDK.comicsResource.getComic(comicId).mapCatching {
                    val storageModel = it.toStorageModel()
                    appDatabase.comicsDao().insertAll(storageModel)
                    storageModel.toDataModel()
                }
            }
        }

    private fun Comic.toStorageModel(): ComicDBEntity = ComicDBEntity(
        id,
        title,
        description,
        // TODO: An optimization that could occur here is being able to select the
        // best image size based on the device's unique window / dpi.
        thumbnail?.getUrl(Image.ImageVariants.PORTRAIT_UNCANNY)
    )

    private fun ComicDBEntity.toDataModel(): ComicData = ComicData(
        id = id,
        title = title,
        description = description,
        thumbnailUrl = thumbnailUrl
    )
}
