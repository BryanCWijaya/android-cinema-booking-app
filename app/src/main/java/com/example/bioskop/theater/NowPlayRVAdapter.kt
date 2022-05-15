package com.example.bioskop.theater

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bioskop.R
import com.example.bioskop.theater_list.TheaterRVAdapter

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val image: ImageView = view.findViewById(R.id.movie_poster)
    val name: TextView = view.findViewById(R.id.movie_name)
    lateinit var id: String
    lateinit var theaterId: String

    init {
        view.setOnClickListener {
            val action = TheaterFragmentDirections.actionTheaterFragmentToMovieTheaterFragment(id, theaterId)
            view.findNavController().navigate(action)
        }
    }
}

class NowPlayRVAdapter(val context: Context, private val data: ArrayList<NowPlayList>, val theaterId: String) :
    RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val _view =
            LayoutInflater.from(parent.context).inflate(R.layout.single_movie_horizontal_scroll, parent, false)
        return ViewHolder(_view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = data[position].title
        Glide.with(holder.image.context).load(data[position].poster_link).into(holder.image)
        holder.id = data[position].m_id
        holder.theaterId = theaterId
    }

    override fun getItemCount() = data.size
}