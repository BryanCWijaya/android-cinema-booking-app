package com.example.bioskop

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.bioskop.homepage.MovieId
import com.example.bioskop.movie.MovieData
import com.example.bioskop.seat.PickedSeat
import com.example.bioskop.theater.NowPlayList
import com.example.bioskop.theater_list.TheaterList
import kotlinx.coroutines.*
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface APIService {
    @GET("/movie/{Id}")
    suspend fun getMovie(@Path("Id") MovieID: Int): Response<MovieData>

    @GET("/movie/0")
    suspend fun getAllMovieId(): Response<ArrayList<MovieId>>

    @GET("/theater")
    suspend fun getAllTheater(): Response<ArrayList<TheaterList>>

    @GET("/nowplaying/{Id}")
    suspend fun getPLayList(@Path("Id") movieID: Int): Response<ArrayList<NowPlayList>>

    @GET("/getseat/{movieID}&{theaterID}")
    suspend fun getPickedSeat(
        @Path("movieID") movieID: String,
        @Path("theaterID") theaterID: String
    ): Response<PickedSeat>

    @POST("/seat/{movieID}&{theaterID}&{seatID}")
    suspend fun postPickedSead(
        @Path("movieID") movieID: String,
        @Path("theaterID") theaterID: String,
        @Path("seatID") seatID: String
    )
}

class MovieAPIService {
    val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.1.10:5000")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val service = retrofit.create(APIService::class.java)

    fun getAllMovieId(data: MutableLiveData<ArrayList<MovieId>?>) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = service.getAllMovieId()

                withContext(Dispatchers.Main.immediate) {
                    if (response.isSuccessful) {
                        data.postValue(response.body())
                    }
                }
            } catch (e: Exception) {
                data.postValue(null)
            }

        }
    }

    fun getMovieDetails(data: MutableLiveData<MovieData>, movieID: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            launch {
                try {
                    val response = service.getMovie(movieID)

                    withContext(Dispatchers.Main.immediate) {
                        val result = response.body()!!
                        result.fitData()
                        data.postValue(result)
                    }
                } catch (e: Exception) {
                    data.postValue(null)
                }
            }
        }
    }

    fun getAllTheater(data: MutableLiveData<ArrayList<TheaterList>?>) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = service.getAllTheater()

                withContext(Dispatchers.Main.immediate) {
                    if (response.isSuccessful) {
                        data.postValue(response.body())
                    }
                }
            } catch (e: Exception) {
                data.postValue(null)
            }
        }
    }

    fun getNowPlay(data: MutableLiveData<ArrayList<NowPlayList>?>, movieID: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = service.getPLayList(movieID)

                withContext(Dispatchers.Main.immediate) {
                    if (response.isSuccessful) {
                        data.postValue(response.body())
                    }
                }
            } catch (e: Exception) {
                data.postValue(null)
            }
        }
    }

    fun getPickedSeat(data: MutableLiveData<PickedSeat?>, movieID: String, theaterID: String) {

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getPickedSeat(movieID, theaterID)
            withContext(Dispatchers.Main.immediate) {
                if (response.isSuccessful) {
                    data.postValue(response.body())
                }
            }
        }
    }

    fun updatePickedSeat(movieID: String, theaterID: String, seatNo: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.postPickedSead(movieID, theaterID, seatNo)
        }
    }
}