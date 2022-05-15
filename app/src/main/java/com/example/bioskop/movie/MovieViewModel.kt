package com.example.bioskop.movie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bioskop.MovieAPIService


class MovieViewModel : ViewModel() {
    private val movieAPIService = MovieAPIService()
    val data = MutableLiveData(MovieData())

    fun loadData(filmID: Int) {
        movieAPIService.getMovieDetails(data, filmID)
    }
}