package com.example.bioskop.homepage

import android.content.res.Configuration
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bioskop.databinding.HomepageFragmentBinding

class HomepageFragment : Fragment() {

    companion object {
        fun newInstance() = HomepageFragment()
    }

    private lateinit var binding : HomepageFragmentBinding
    private lateinit var viewModel: HomepageViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HomepageFragmentBinding.inflate(inflater, container, false)
        val _view = binding.root
        viewModel = ViewModelProvider(this).get(HomepageViewModel::class.java)
        binding.homepageVM = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.movieList.layoutManager = GridLayoutManager(activity, 2)

        viewModel.data.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visibility = View.GONE
            it?.let {
                binding.movieList.adapter = HomepageRVAdapter(requireActivity(), it)
            } ?: let    {
                binding.txtError.visibility = View.VISIBLE
            }
        })

        return _view
    }



}