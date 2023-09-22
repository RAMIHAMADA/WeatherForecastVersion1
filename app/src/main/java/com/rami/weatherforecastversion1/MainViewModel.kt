package com.rami.weatherforecastversion1

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rami.weatherforecastversion1.DayItem

class MainViewModel: ViewModel() {
    val liveDataCurrent = MutableLiveData<DayItem>()
    val liveDataList = MutableLiveData<List<String>>()
}