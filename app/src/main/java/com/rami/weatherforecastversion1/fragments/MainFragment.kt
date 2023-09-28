package com.rami.weatherforecastversion1.fragments

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.tabs.TabLayoutMediator
import com.rami.weatherforecastversion1.R
import com.rami.weatherforecastversion1.adapters.ViewPagerAdapter
import com.rami.weatherforecastversion1.data.WeatherModel
import com.rami.weatherforecastversion1.databinding.FragmentMainBinding
import com.rami.weatherforecastversion1.viewmodel.MainViewModel
import com.squareup.picasso.Picasso
import org.json.JSONObject

const val API_KAY = "43a8ac71df6347548d1144132232109"

class MainFragment : Fragment() {
    private lateinit var pLauncher: ActivityResultLauncher<String>
    private lateinit var binding: FragmentMainBinding
    private val fragmentList = listOf(
        HoursFragment.newInstance(),
        DaysFragment.newInstance()
    )

    private val model: MainViewModel by activityViewModels()

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
        updateCurrentCard()
        requestWeatherData("London")
    }

    private fun init() = with(binding) {
        val adapter = ViewPagerAdapter(activity as AppCompatActivity, fragmentList)
        viewPager.adapter = adapter
        tabLayoutMediator()
    }

    private fun updateCurrentCard() = with(binding) {
        model.liveDataCurrent.observe(viewLifecycleOwner) {
            val maxMinTemp = "${it.maxTemp}C / ${it.minTemp}C"
            dateTv.text = it.time
            cityTv.text = it.city
            currentTempTv.text = "${it.currentTemp}C"
            conditionTv.text = it.condition
            maxMinTv.text = maxMinTemp
            Picasso.get().load(it.imageUrl).into(weatherIv)


        }
    }

    private fun parsWeatherData(result: String) {
        val mainObject = JSONObject(result)
        val list = parseDays(mainObject)
        parseCurrentData(mainObject, list[0])
    }

    private fun parseDays(mainObject: JSONObject): List<WeatherModel> {
        val list = ArrayList<WeatherModel>()
        val daysArray = mainObject.getJSONObject("forecast").getJSONArray("forecastday")
        val name = mainObject.getJSONObject("location").getString("name")
        for (i in 0 until daysArray.length()) {
            val day = daysArray[i] as JSONObject
            val item = WeatherModel(
                name,
                day.getString("day"),
                day.getJSONObject("day").getJSONObject("condition").getString("text"),
                "",
                day.getJSONObject("day").getString("maxtemp_c"),
                day.getJSONObject("day").getString("mintemp_c"),
                day.getJSONObject("day").getJSONObject("condition").getString("icon"),
                day.getJSONArray("hour").toString()
            )
            list.add(item)
        }
        return list
    }

    private fun parseCurrentData(mainObject: JSONObject, weatherItem: WeatherModel) {
        val item = WeatherModel(
            mainObject.getJSONObject("location").getString("name"),
            mainObject.getJSONObject("current").getString("last_updated"),
            mainObject.getJSONObject("current").getJSONObject("condition").getString("text"),
            mainObject.getJSONObject("current").getString("temp_c"),
            weatherItem.maxTemp,
            weatherItem.minTemp,
            mainObject.getJSONObject("current").getJSONObject("condition").getString("icon"),
            weatherItem.hours
        )
        model.liveDataCurrent.value = item
        Log.e("COK", "MaxTemp: ${item.maxTemp}")
        Log.e("COK", "MinTemp: ${item.minTemp}")
        Log.e("COK", "Hours: ${item.hours}")
    }

    private fun requestWeatherData(city: String) {
        val url = "https://api.weatherapi.com/v1/forecast.json?" +
                "key=$API_KAY" +
                "&q=" +
                city +
                "&days=3" +
                "&aqi=no&alerts=no"
        val queue: RequestQueue = Volley.newRequestQueue(context)

        val request = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                parsWeatherData(response)
            },
            { error ->
                Log.e("COK", "$error")
            }
        )

        queue.add(request)
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