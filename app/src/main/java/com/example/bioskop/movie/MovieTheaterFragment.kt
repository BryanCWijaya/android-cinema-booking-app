package com.example.bioskop.movie

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.bioskop.databinding.FragmentMovieTheaterBinding
import com.example.bioskop.databinding.MovieFragmentBinding
import com.example.bioskop.homepage.HomepageFragmentDirections
import com.example.bioskop.homepage.HomepageRVAdapter

class MovieTheaterFragment : Fragment() {

    companion object {
        fun newInstance() = MovieFragment()
    }

    private lateinit var binding : FragmentMovieTheaterBinding
    private lateinit var viewModel: MovieTheaterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieTheaterBinding.inflate(inflater, container, false)
        val _view = binding.root

        viewModel = ViewModelProvider(this).get(MovieTheaterViewModel::class.java)
        binding.movieTheaterVM = this.viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val movieId = MovieTheaterFragmentArgs.fromBundle(requireArguments()).movieId
        val theaterId = MovieTheaterFragmentArgs.fromBundle(requireArguments()).theaterId
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

        binding.pesan.setOnClickListener {
            val action = MovieTheaterFragmentDirections.actionMovieTheaterFragmentToSeatFragment(movieId, theaterId)
            _view.findNavController().navigate(action)
        }

        return _view
    }
}