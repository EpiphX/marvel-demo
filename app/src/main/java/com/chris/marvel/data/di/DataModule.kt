package com.chris.marvel.data.di

import com.chris.api.MarvelSDK
import com.chris.api.MarvelSDKApi
import com.chris.marvel.BuildConfig
import com.chris.marvel.data.comics.ComicsRepository
import com.chris.marvel.data.comics.ComicsRepositoryImpl
import com.chris.marvel.data.storage.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DataModule {
    @Provides
    fun provideMarvelSDK(): MarvelSDKApi {
        return MarvelSDK.initialize(BuildConfig.MARVEL_PRIVATE_KEY, BuildConfig.MARVEL_PUBLIC_KEY)
    }

    @Provides
    fun provideComicsRepository(
        marvelSDKApi: MarvelSDKApi,
        appDatabase: AppDatabase
    ): ComicsRepository {
        return ComicsRepositoryImpl(marvelSDKApi, appDatabase)
    }
}