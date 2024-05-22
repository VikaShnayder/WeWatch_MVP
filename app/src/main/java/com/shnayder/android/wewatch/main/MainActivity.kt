package com.shnayder.android.wewatch.main

import com.shnayder.android.wewatch.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.asLiveData

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.shnayder.android.wewatch.add.AddActivity
import com.shnayder.android.wewatch.model.FilmDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity(){
    private lateinit var openAddFilm: FloatingActionButton
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MainAdapter
    private lateinit var layoutEmptyDB: LinearLayout
    private lateinit var dataBase: FilmDatabase
    private lateinit var deleteBtn: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        openAddFilm = findViewById(R.id.open_add_film)
        recyclerView = findViewById(R.id.recyclerView_full_filmDB)
        recyclerView.layoutManager = LinearLayoutManager(this)
        layoutEmptyDB = findViewById(R.id.liner_layout_empty_filmDB)
        deleteBtn = findViewById(R.id.delete_film)

        dataBase = FilmDatabase.getDatabase(this@MainActivity)


        openAddFilm.setOnClickListener{
            val intent = Intent(this@MainActivity, AddActivity::class.java)
            startActivityForResult(intent,1)
        }
        loadLocalDB()

        //delete
        deleteBtn.setOnClickListener {
            val films = adapter!!.selectedMovies
            CoroutineScope(Dispatchers.IO).launch {
                for (film in films) {
                    dataBase.getFilmDao().delete(film)
                }
            }

            if (films.size == 1) {
                Toast.makeText(this, "Фильм удален", Toast.LENGTH_LONG).show()
            } else if (films.size > 1) {
                Toast.makeText(this, "Фильмы удалены", Toast.LENGTH_LONG).show()
            }
            loadLocalDB()
        }

    }
    private fun loadLocalDB() {
        dataBase.getFilmDao().getAll().asLiveData().observe(this@MainActivity) {film ->
            if (film.isNotEmpty()) {
                layoutEmptyDB.visibility = View.INVISIBLE
                recyclerView.visibility = View.VISIBLE
                adapter = MainAdapter(film, this@MainActivity)
                recyclerView.adapter = adapter
            } else {
                recyclerView.visibility = View.INVISIBLE
                layoutEmptyDB.visibility = View.VISIBLE
            }
        }
    }

}