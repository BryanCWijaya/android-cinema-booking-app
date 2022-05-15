package com.example.bioskop.theater

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.example.bioskop.MovieAPIService

class TheaterViewModel : ViewModel() {
    private val movieAPIService = MovieAPIService()
    lateinit var imageURL: String
    lateinit var location: String
    lateinit var theaterName: String
    var data = MutableLiveData<ArrayList<NowPlayList>?>()

    fun loadData(movieID: Int) {
        movieAPIService.getNowPlay(data, movieID)
    }

    companion object {
        @JvmStatic
        @BindingAdapter("theater_image")
        fun loadImage(view: ImageView, t_image: String?) {
            t_image?.let { Glide.with(view.context)
                .load(it)
                .into(view)
            }}
    }
}