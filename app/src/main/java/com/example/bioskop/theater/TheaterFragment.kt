package com.example.bioskop.theater

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bioskop.databinding.TheaterFragmentBinding
import com.example.bioskop.homepage.HomepageRVAdapter

class TheaterFragment : Fragment() {

    companion object {
        fun newInstance() = TheaterFragment()
    }

    private lateinit var binding : TheaterFragmentBinding
    private lateinit var viewModel: TheaterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TheaterFragmentBinding.inflate(inflater, container, false)
        val _view = binding.root

        viewModel = ViewModelProvider(this).get(TheaterViewModel::class.java)
        binding.theaterVM = this.viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val data = TheaterFragmentArgs.fromBundle(requireArguments())
        viewModel.theaterName = data.theaterName
        viewModel.location = data.location
        viewModel.imageURL = data.imageUrl

        viewModel.loadData(Integer.parseInt(data.theaterId))

        binding.nowPlayList.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        viewModel.data.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visibility = View.GONE
            it?.let {
                binding.nowPlayList.adapter = NowPlayRVAdapter(requireActivity(), it, data.theaterId)
            } ?: let{
                binding.txtError.visibility = View.VISIBLE
            }
        })
        return _view
    }

}