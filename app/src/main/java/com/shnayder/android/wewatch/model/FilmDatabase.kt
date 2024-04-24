package com.shnayder.android.wewatch.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FilmEntity::class], version = 1)
abstract class FilmDatabase : RoomDatabase(){
    abstract fun getFilmDao(): FilmDao

    companion object {
        @Volatile
        private var INSTANCE: FilmDatabase? = null
        fun getDatabase(context: Context): FilmDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        FilmDatabase::class.java,
                        "film_database.db"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}