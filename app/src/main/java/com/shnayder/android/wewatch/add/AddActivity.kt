package com.shnayder.android.wewatch.add

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.shnayder.android.wewatch.SearchActivity
import com.shnayder.android.wewatch.model.FilmDatabase
import com.shnayder.android.wewatch.model.FilmEntity
import com.shnayder.android.wewatch.retrofit.Film
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddActivity : AppCompatActivity() {
    private lateinit var title: EditText
    private lateinit var year: EditText
    private lateinit var poster: ImageView
    private lateinit var searchFilm: ImageView
    private lateinit var addFilm: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        val dataBase = FilmDatabase.getDatabase(this@AddActivity)

        title = findViewById(R.id.enter_title_film)
        year = findViewById(R.id.enter_year_film)
        poster = findViewById(R.id.poster_film)
        searchFilm = findViewById(R.id.search_film)
        addFilm = findViewById(R.id.add_film)



        searchFilm.setOnClickListener{
            val intentAddToSearch = Intent(this@AddActivity, SearchActivity::class.java)
            intentAddToSearch.putExtra("title_key",title.text.toString())
            //intent.putExtra("year",year)
            startActivityForResult(intentAddToSearch,2)
        }


        addFilm.setOnClickListener{
            if (title.text.isEmpty() || year.text.isEmpty()) {
                Toast.makeText(this,"Поля 'Заголовок' и 'Год релиза' не может быть пустыми", Toast.LENGTH_LONG).show()
            }
            else {
                if (poster.tag == null) poster.tag = ""
                CoroutineScope(Dispatchers.IO).launch {
                    val film = FilmEntity(null, title.text.toString(), year.text.toString(), poster.tag.toString())
                    dataBase.getFilmDao().insert(film)
                    //Log.d("getAll=",dataBase.getFilmDao().getAll())
                    //?
                }
                setResult(Activity.RESULT_OK)
                finish()
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        this@AddActivity.runOnUiThread {
            title.setText(data?.getStringExtra("search_title"))
            year.setText(data?.getStringExtra("search_year"))
            poster.tag = data?.getStringExtra("search_poster") ?: ""
            if (poster.tag !== "") {
                Glide
                    .with(this)
                    .load(poster.tag)
                    .into(poster);
            }
        }

    }
}