package com.chris.marvel.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chris.marvel.data.storage.dao.ComicDao
import com.chris.marvel.data.storage.entities.ComicDBEntity

@Database(entities = [ComicDBEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun comicsDao(): ComicDao
}