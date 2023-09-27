package com.rami.weatherforecastversion1.api

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

const val API_KAY = "43a8ac71df6347548d1144132232109"

class RequestWeatherData(private val context: Context) {

    fun requestWeatherData(city: String) {
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
                Log.e("COK", "$response" )
            },
            { error ->
                Log.e("COK", "$error")
            }
        )

        queue.add(request)
    }
}