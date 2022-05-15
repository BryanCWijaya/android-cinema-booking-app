package com.example.bioskop

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.bioskop.movie.MovieFragmentArgs
import com.example.bioskop.seat.SeatFragmentArgs

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PaymentFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PaymentFragment : Fragment() {

    private lateinit var image: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val movieId = PaymentFragmentArgs.fromBundle(requireArguments()).movieID
        val seatNumber = PaymentFragmentArgs.fromBundle(requireArguments()).seatNo
        val theaterId = PaymentFragmentArgs.fromBundle(requireArguments()).theaterID

        val _view = inflater.inflate(R.layout.fragment_payment, container, false)
        image = _view.findViewById(R.id.imageView)
        image.setOnClickListener {
            Toast.makeText(activity, "Pembayaran Berhasil!", Toast.LENGTH_LONG).show()
            MovieAPIService().updatePickedSeat(movieId, theaterId, seatNumber)
            _view.findNavController().navigate(R.id.action_paymentFragment_to_homepageFragment)

        }

        return _view
    }
}