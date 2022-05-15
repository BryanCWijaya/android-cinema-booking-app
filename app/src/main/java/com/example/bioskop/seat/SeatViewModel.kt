package com.example.bioskop.seat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bioskop.MovieAPIService
import com.example.bioskop.movie.MovieData

class SeatViewModel: ViewModel() {
    private val movieAPIService = MovieAPIService()
    val data = MutableLiveData<PickedSeat?>()

    fun loadData(movieId: String, theaterID: String) {
        movieAPIService.getPickedSeat(data, movieId, theaterID)
    }
}