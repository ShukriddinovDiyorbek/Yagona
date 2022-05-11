package uz.transport.yagonatransportchiptasi.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import uz.transport.yagonatransportchiptasi.R
import uz.transport.yagonatransportchiptasi.adapter.BusTicketAdapter
import uz.transport.yagonatransportchiptasi.databinding.FragmentBusBinding
import uz.transport.yagonatransportchiptasi.extensions.Extensions
import uz.transport.yagonatransportchiptasi.model.BusTicket


class BusFragment : Fragment() {
    lateinit var binding: FragmentBusBinding
    var departureTime:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        departureTime = arguments?.get("date").toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_bus, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBusBinding.bind(view)
        initViews()
    }

    @SuppressLint("SetTextI18n")
    private fun initViews() {
        refreshAdapter(allTickets())

        binding.tvDirection.text = "${Extensions.loadData(requireContext())}-${Extensions.loadData2(requireContext())}"

        binding.tvDate.text = departureTime

        binding.ivBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun refreshAdapter(items: ArrayList<BusTicket>) {
        val adapter = BusTicketAdapter(requireContext(), items)
        binding.recyclerView.adapter = adapter
    }

    private fun allTickets(): ArrayList<BusTicket> {
        val items = ArrayList<BusTicket>()

        items.add(BusTicket("", "", "", "", "", "", "", "", ""))
        items.add(BusTicket("", "", "", "", "", "", "", "", ""))
        items.add(BusTicket("", "", "", "", "", "", "", "", ""))
        items.add(BusTicket("", "", "", "", "", "", "", "", ""))
        items.add(BusTicket("", "", "", "", "", "", "", "", ""))
        items.add(BusTicket("", "", "", "", "", "", "", "", ""))
        items.add(BusTicket("", "", "", "", "", "", "", "", ""))
        items.add(BusTicket("", "", "", "", "", "", "", "", ""))
        items.add(BusTicket("", "", "", "", "", "", "", "", ""))
        items.add(BusTicket("", "", "", "", "", "", "", "", ""))
        items.add(BusTicket("", "", "", "", "", "", "", "", ""))


        return items
    }
}