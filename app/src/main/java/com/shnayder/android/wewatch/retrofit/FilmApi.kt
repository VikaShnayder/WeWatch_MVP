package com.shnayder.android.wewatch.retrofit

import com.shnayder.android.wewatch.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

/*интерфейс продукт api
(прописываем запросы, которые будет отправлять retrofit/
GET-получить)*/

interface FilmApi {
    @GET("/")
    suspend fun getFilmByTitle(@Query("apikey") apikey: String, @Query("s") title: String): SearchResponse

}




/*
    @GET("/")
    suspend fun getFilmByTitleAndYear(@Query("apikey") apikey: String, @Query("s") title: String, @Query("y") year: String): SearchResponse
    */