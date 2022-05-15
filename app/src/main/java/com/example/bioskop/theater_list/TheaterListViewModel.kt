package com.example.bioskop.theater_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bioskop.MovieAPIService
import com.example.bioskop.homepage.MovieId

class TheaterListViewModel : ViewModel() {
    var data = MutableLiveData<ArrayList<TheaterList>?>()
    private val movieAPIService = MovieAPIService()

    init {
        movieAPIService.getAllTheater(data)
    }}