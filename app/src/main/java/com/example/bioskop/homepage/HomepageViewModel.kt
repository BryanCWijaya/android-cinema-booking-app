package com.example.bioskop.homepage

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bioskop.MovieAPIService

class HomepageViewModel : ViewModel() {
    var data = MutableLiveData<ArrayList<MovieId>?>()
    private val movieAPIService = MovieAPIService()
    init {
        movieAPIService.getAllMovieId(data)
    }
}