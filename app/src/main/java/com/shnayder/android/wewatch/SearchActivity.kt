package com.shnayder.android.wewatch

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shnayder.android.wewatch.retrofit.Film
import com.shnayder.android.wewatch.retrofit.FilmApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class SearchActivity : AppCompatActivity() {
    private lateinit var list: List<Film>

    private lateinit var adapter: SearchAdapter
    private lateinit var recyclerView: RecyclerView

    private var titleIntentResponce: String =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        titleIntentResponce =  intent.getStringExtra("title_key") ?: ""

        recyclerView = findViewById(R.id.all_film_search)
        recyclerView.layoutManager = LinearLayoutManager(this)

        //перехватваем запросы body. okhttp
        val interseptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(interseptor)
            .build()

        //базовая инстанция
        val retrrofit = Retrofit.Builder().client(client)
            .baseUrl("https://www.omdbapi.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        val filmApi = retrrofit.create(FilmApi::class.java)

        CoroutineScope(Dispatchers.Main).launch {
            var filmResponce  = filmApi.getFilmByTitle("e451f001", titleIntentResponce)
            runOnUiThread {
                list = filmResponce.items?: emptyList()
                //рисует данные
                adapter = SearchAdapter(list, itemListener,this@SearchActivity)
                //помещает
                recyclerView.adapter = adapter
            }
        }
    }

    internal var itemListener: RecyclerItemListener = object : RecyclerItemListener {
        override fun onItemClick(view: View, position: Int) {
            val film = adapter.getItemPosition(position)
            //Log.d(TAG, movie.title)
            //Log.d(TAG, movie.getReleaseYearFromDate().toString())
            val intentSearchToAdd = Intent(this@SearchActivity, AddActivity::class.java)
            intentSearchToAdd.putExtra("search_title", film.title)
            intentSearchToAdd.putExtra("search_year", film.getReleaseYearFromDate().toString())
            intentSearchToAdd.putExtra("search_poster", film.posterPath)
            setResult(Activity.RESULT_OK, intentSearchToAdd)
            finish()
        }
    }

    interface RecyclerItemListener {
        fun onItemClick(v: View, position: Int)
    }

}
