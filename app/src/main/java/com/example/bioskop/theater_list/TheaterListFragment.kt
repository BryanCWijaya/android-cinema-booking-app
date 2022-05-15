package com.example.bioskop.theater_list

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bioskop.databinding.TheaterFragmentBinding
import com.example.bioskop.databinding.TheaterListFragmentBinding

class TheaterListFragment : Fragment() {

    companion object {
        fun newInstance() = TheaterListFragment()
    }

    private lateinit var binding : TheaterListFragmentBinding
    private lateinit var viewModel: TheaterListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(TheaterListViewModel::class.java)

        binding = TheaterListFragmentBinding.inflate(inflater, container, false)
        val _view = binding.root
        binding.theaterListVM = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.theaterList.layoutManager = LinearLayoutManager(activity)

        viewModel.data.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visibility = View.GONE
            it?.let {
                binding.theaterList.adapter = TheaterRVAdapter(requireActivity(), it)
            } ?: let    {
                binding.txtError.visibility = View.VISIBLE
            }
        })

        return _view
    }

}