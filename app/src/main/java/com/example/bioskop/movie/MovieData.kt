package com.example.bioskop.movie

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import org.json.JSONArray

data class MovieData(
    var genre: String = "",
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val poster_link: String = "",
    var production: String = "",
    val release_date: String = "",
    val runtime: String = "",
    val tag: String = "",
    val vote_average: String = "",
) {
    fun fitData() {
        val jsonArrayGenre = JSONArray(genre)
        val genreList = mutableListOf<String>()
        for (i in 0..jsonArrayGenre.length() - 1) {
            val jsonObject = jsonArrayGenre.getJSONObject(i)
            genreList.add(jsonObject.get("name").toString())
        }
        genre = genreList.joinToString(separator = ", ")

        val jsonArrayProduction = JSONArray(production)
        val productionList = mutableListOf<String>()
        for (i in 0..jsonArrayProduction.length() - 1) {
            val jsonObject = jsonArrayProduction.getJSONObject(i)
            productionList.add(jsonObject.get("name").toString())
        }
        production = productionList.joinToString(separator = ", ")
    }

    companion object {
        @JvmStatic
        @BindingAdapter("poster")
        fun loadImage(view: ImageView, poster: String?) {
            poster?.let { Glide.with(view.context)
                .load(it)
                .into(view)
        }}
    }
}
