package com.rami.weatherforecastversion1.fragments

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.rami.weatherforecastversion1.R
import com.rami.weatherforecastversion1.adapters.ViewPagerAdapter
import com.rami.weatherforecastversion1.api.RequestWeatherData
import com.rami.weatherforecastversion1.databinding.FragmentMainBinding


class MainFragment : Fragment() {
    private lateinit var pLauncher: ActivityResultLauncher<String>
    private lateinit var binding: FragmentMainBinding
    private val fragmentList = listOf(
        HoursFragment.newInstance(),
        DaysFragment.newInstance()
    )
    private val requestWeatherData = RequestWeatherData(requireContext())


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermissions()
        init()
        requestWeatherData.requestWeatherData("London")
    }

    private fun init() = with(binding) {
        val adapter = ViewPagerAdapter(activity as AppCompatActivity, fragmentList)
        viewPager.adapter = adapter
        tabLayoutMediator()
    }

    private fun tabLayoutMediator() = with(binding) {
        val tabsList = tabsList()
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabsList[position]
        }.attach()

    }

    private fun tabsList(): List<String> {
       return listOf(
            getString(R.string.hours),
            getString(R.string.days)
        )
    }

    private fun permissionListener() {
        pLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            Toast.makeText(activity, "Permissions is  $it", Toast.LENGTH_LONG).show()
        }
    }

    private fun checkPermissions() {
        if (!isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
            permissionListener()
            pLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }

}