package com.shnayder.android.wewatch.main

import com.shnayder.android.wewatch.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shnayder.android.wewatch.model.FilmEntity
import com.shnayder.android.wewatch.retrofit.Film
import java.util.HashSet


class MainAdapter(private val film: List<FilmEntity>, var context: Context): RecyclerView.Adapter<MainAdapter.FilmViewHolder>() {

    val selectedMovies = HashSet<FilmEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_activity_main, parent, false)
        val viewHolder = FilmViewHolder(view)
        return viewHolder
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val currentFilm = film[position]
        holder.titleTextView.text = "${currentFilm.title}"
        holder.yearTextView.text = "${currentFilm.year}"

        if (film[position].posterUrl.equals("")) {
            holder.posterImageView.setImageDrawable(context.getDrawable(R.drawable.ic_baseline_movie_24))
        } else {
            Glide.with(context)
                .load(film[position].posterUrl) // Путь к файлу изображения
                .into(holder.posterImageView)
        }
    }
    override fun getItemCount(): Int {
        return film.size
    }
    inner class FilmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.title_film)
        val yearTextView: TextView = itemView.findViewById(R.id.year_film)
        val posterImageView: ImageView = itemView.findViewById(R.id.image_film)
        var checkBox: CheckBox = itemView.findViewById(R.id.check_box)

        init {
            checkBox.setOnClickListener {
                val adapterPosition = adapterPosition
                if (!selectedMovies.contains(film[adapterPosition])) {
                    checkBox.isChecked = true
                    selectedMovies.add(film[adapterPosition])
                } else {
                    checkBox.isChecked = false
                    selectedMovies.add(film[adapterPosition])
                }
            }
        }
    }
}