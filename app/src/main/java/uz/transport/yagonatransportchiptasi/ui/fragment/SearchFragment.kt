package uz.transport.yagonatransportchiptasi.ui.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import uz.transport.yagonatransportchiptasi.R
import uz.transport.yagonatransportchiptasi.databinding.FragmentSearchBinding
import uz.transport.yagonatransportchiptasi.extensions.Extensions.loadData
import uz.transport.yagonatransportchiptasi.extensions.Extensions.loadData2
import uz.transport.yagonatransportchiptasi.ui.activity.CalendarActivity
import uz.transport.yagonatransportchiptasi.ui.activity.FromActivity
import uz.transport.yagonatransportchiptasi.ui.activity.ToActivity
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    var departureDateTime: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)
        initViews()
    }


    private fun initViews() {

        binding.apply {
            ivChange.setOnClickListener {
                changeDestinations()
            }


            /**
             * this is Select the Departure date from the calendar
             */
            llDeparture.setOnClickListener {
                openCalendarActivity(1)

            }
            /**
             * this is Select the Arrival date from the calendar
             */
            llArrival.setOnClickListener {
                openCalendarActivity(2)
            }

            tvDeparture.text = giveDate()
            departureDateTime = giveDate()

            btnSearch.setOnClickListener {
                if (tvFrom.text.toString().trim().lowercase() == "moskva") {
                    openWaytoGoFragment()
                } else {
                    openPassengerSetupFragment()
                }
            }

            ivClose.setOnClickListener {
                tvArrival.text = "Orqaga"
                ivClose.visibility = View.GONE
            }
            tvFrom.setOnClickListener {
                openFromFragment()
            }
            tvTo.setOnClickListener {
                openToFragment()
            }
        }
    }

    private fun openWaytoGoFragment() {
        findNavController().navigate(R.id.action_searchFragment_to_waytoGoFragment)
    }

    override fun onResume() {
        super.onResume()
        binding.tvFrom.text = loadData(requireContext())
        binding.tvTo.text = loadData2(requireContext())
    }


    private fun openPassengerSetupFragment() {
        findNavController().navigate(R.id.action_searchFragment_to_allTransportFragment, bundleOf("date" to departureDateTime))
    }

    private fun openFromFragment() {
        val intent = Intent(requireContext(), FromActivity::class.java)
        startActivity(intent)
    }

    private fun openToFragment() {
        val intent = Intent(requireContext(), ToActivity::class.java)
        startActivity(intent)
    }

    @SuppressLint("SimpleDateFormat")
    fun giveDate(): String {
        val cal: Calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("EEE, MMM d")
        return sdf.format(cal.getTime())
    }

    private fun openCalendarActivity(type: Int) {
        val intent = Intent(requireContext(), CalendarActivity::class.java)
        intent.putExtra("locationStart", loadData(requireContext()))
        intent.putExtra("locationEnd", loadData2(requireContext()))
        intent.putExtra("type", "$type")
        postActivity.launch(intent)
    }

    @SuppressLint("SimpleDateFormat")
    var postActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val day = data!!.getStringExtra("day")!!.toInt()
            val month = data.getStringExtra("month")!!
            val year = data.getStringExtra("year")!!
            val type = data.getStringExtra("type")!!

            /**
             * this is change the time format
             */
            val originalFormat = SimpleDateFormat("dd MM yyyy")
            val targetFormat = SimpleDateFormat("EEE, MMM d")
            val date: Date
            try {
                date = originalFormat.parse("$day $month $year")
                if (type.toInt() == 1) {
                    binding.tvDeparture.text = targetFormat.format(date).toString()
                    departureDateTime = targetFormat.format(date).toString()
                } else {
                    binding.tvArrival.text = targetFormat.format(date).toString()
                    binding.ivClose.visibility = View.VISIBLE
                }
            } catch (ex: ParseException) {

            }

        }
    }

    //to change directions
    private fun changeDestinations() {
        binding.apply {
            if (tvFrom.text.isNotEmpty() && tvTo.text.isNotEmpty()) {
                val departure = tvFrom.text
                tvFrom.text = tvTo.text
                tvTo.text = departure
            }
        }
    }
}