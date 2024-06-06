package com.chris.marvel.data.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.chris.marvel.data.storage.entities.ComicDBEntity

@Dao
interface ComicDao {
    @Query("SELECT * FROM comics WHERE id = :id")
    fun getById(id: String): ComicDBEntity?

    @Insert
    fun insertAll(vararg comics: ComicDBEntity)
}