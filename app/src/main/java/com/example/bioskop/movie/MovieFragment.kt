package com.example.bioskop.movie

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.bioskop.databinding.MovieFragmentBinding
import com.example.bioskop.homepage.HomepageFragmentDirections
import com.example.bioskop.homepage.HomepageRVAdapter

class MovieFragment : Fragment() {

    companion object {
        fun newInstance() = MovieFragment()
    }

    private lateinit var binding : MovieFragmentBinding
    private lateinit var viewModel: MovieViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MovieFragmentBinding.inflate(inflater, container, false)
        val _view = binding.root

        viewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        binding.movieVM = this.viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val movieId = MovieFragmentArgs.fromBundle(requireArguments()).movieId
        viewModel.loadData(Integer.parseInt(movieId))

        viewModel.data.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it.title != "") {
                    binding.progressBar.visibility = View.GONE
                }
            } ?: let{
                binding.progressBar.visibility = View.GONE
                binding.txtError.visibility = View.VISIBLE
            }
        })

        return _view
    }
}