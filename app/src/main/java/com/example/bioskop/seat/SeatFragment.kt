package com.example.bioskop.seat

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.bioskop.MovieAPIService
import com.example.bioskop.databinding.SeatFragmentBinding
import com.example.bioskop.movie.MovieViewModel

class SeatFragment : Fragment() {

    companion object {
        fun newInstance() = SeatFragment()
    }

    private lateinit var binding: SeatFragmentBinding
    private lateinit var viewModel: SeatViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SeatFragmentBinding.inflate(inflater, container, false)
        val _view = binding.root
        val api = MovieAPIService()
        val movieId = SeatFragmentArgs.fromBundle(requireArguments()).movieID
        val theaterID = SeatFragmentArgs.fromBundle(requireArguments()).seatID
        viewModel = ViewModelProvider(this).get(SeatViewModel::class.java)
        viewModel.loadData(movieId, theaterID)
        val btnList = listOf<Button>(
            binding.button1,
            binding.button2,
            binding.button3,
            binding.button4,
            binding.button5,
            binding.button6,
            binding.button7,
            binding.button8
        )
        viewModel.data.observe(viewLifecycleOwner, Observer {
            it?.let {
                val seats = it.picked_seat
                Log.d("abcd", seats.toString())
                val seatList: List<String> = seats?.split(",")?.toList() ?: listOf()
                for (i in 0..7) {
                    if ((i + 1).toString() in seatList) {
                        btnList[i].setBackgroundColor(Color.RED);
                        btnList[i].setOnClickListener {
                            Toast.makeText(activity, "Kursi sudah di pesan", Toast.LENGTH_SHORT)
                                .show()
                        }
                    } else {
                        btnList[i].setBackgroundColor(Color.GREEN);
                        btnList[i].setOnClickListener {
                            val action = SeatFragmentDirections.actionSeatFragmentToPaymentFragment(
                                movieID = movieId,
                                seatNo = (i + 1).toString(),
                                theaterID = theaterID
                            )
                            _view.findNavController().navigate(action)
                        }
                    }
                }
            }
        })
//        binding.button1.setOnClickListener {
//
//        }

        return _view
    }

}