package com.example.bioskop.homepage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bioskop.R

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val image: ImageView = view.findViewById(R.id.movie_poster)
    val name: TextView = view.findViewById(R.id.movie_name)
    lateinit var id: String
    init {
        view.setOnClickListener {
            val action = HomepageFragmentDirections.actionHomepageFragmentToMovieFragment(id)
            view.findNavController().navigate(action)
        }
    }
}

class HomepageRVAdapter(val context: Context, private val data: ArrayList<MovieId>) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val _view = LayoutInflater.from(parent.context).inflate(R.layout.single_movie, parent, false)
        return ViewHolder(_view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = data[position].title
        Glide.with(holder.image.context).load(data[position].poster_link).into(holder.image)
        holder.id = data[position].id
    }

    override fun getItemCount() = data.size
}