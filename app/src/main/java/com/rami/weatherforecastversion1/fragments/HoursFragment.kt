package com.rami.weatherforecastversion1.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.rami.weatherforecastversion1.adapters.WeatherAdapter
import com.rami.weatherforecastversion1.data.WeatherModel
import com.rami.weatherforecastversion1.databinding.FragmentHoursBinding


class HoursFragment : Fragment() {
    private lateinit var binding: FragmentHoursBinding
    private lateinit var adapter: WeatherAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHoursBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycleView()
    }

    private fun initRecycleView() = with(binding) {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = WeatherAdapter()
        recyclerView.adapter = adapter
        val list = listOf(
            WeatherModel(
                "", "12:00", "Sunny", "25C",
                "", "", "", ""
            ),
            WeatherModel(
                "", "13:00", "Sunny", "20C",
                "", "", "", ""
            ),
            WeatherModel(
                "", "14:00", "Sunny", "15C",
                "", "", "", ""
            )
        )

        adapter.submitList(list)
    }

    companion object {
        @JvmStatic
        fun newInstance() = HoursFragment()
    }
}