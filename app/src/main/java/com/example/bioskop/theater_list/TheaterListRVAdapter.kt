package com.example.bioskop.theater_list

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
    val image: ImageView = view.findViewById(R.id.theater_poster)
    val name: TextView = view.findViewById(R.id.theater_name)
    val location: TextView = view.findViewById(R.id.theater_location)
    lateinit var id: String
    lateinit var image_url: String
    init {
        view.setOnClickListener {
            val action = TheaterListFragmentDirections.actionTheaterListFragmentToTheaterFragment(name.text.toString(), id, location.text.toString(), image_url)
            view.findNavController().navigate(action)
        }
    }
}

class TheaterRVAdapter (val context: Context, private val data: ArrayList<TheaterList>) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val _view = LayoutInflater.from(parent.context).inflate(R.layout.single_theater, parent, false)
        return ViewHolder(_view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = data[position].t_name
        Glide.with(holder.image.context).load(data[position].t_pict).into(holder.image)
        holder.location.text = data[position].t_location
        holder.id = data[position].t_id
        holder.image_url = data[position].t_pict
    }

    override fun getItemCount() = data.size
}