package com.shnayder.android.wewatch

import androidx.lifecycle.LiveData
import com.shnayder.android.wewatch.retrofit.Film
import com.shnayder.android.wewatch.model.FilmDao
import com.shnayder.android.wewatch.model.FilmEntity
import kotlinx.coroutines.flow.Flow

////репозиторий, который будет обращаться к dao-интерфейсу и манипулировать данными базы данных
class FilmRepository(private val filmDao: FilmDao) {

    suspend fun getAll(){
        val allFilms: Flow<List<FilmEntity>> = filmDao.getAll()
    }

    suspend fun insert(film: FilmEntity){
        filmDao.insert(film)
    }

    suspend fun delete(film: FilmEntity){
        filmDao.delete(film)
    }

    suspend fun update(film: FilmEntity){
        filmDao.update(film)
    }
}
