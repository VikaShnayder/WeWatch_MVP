package com.shnayder.android.wewatch.model

import androidx.lifecycle.LiveData
import androidx.room.*
import com.shnayder.android.wewatch.retrofit.Film
import kotlinx.coroutines.flow.Flow

@Dao
interface FilmDao {
    @Insert
    suspend fun insert(film: FilmEntity)

    @Update
    suspend fun update(film: FilmEntity)


    @Query("SELECT * FROM film")
    fun getAll(): Flow<List<FilmEntity>>

    @Delete
    suspend fun delete(film: FilmEntity)
}