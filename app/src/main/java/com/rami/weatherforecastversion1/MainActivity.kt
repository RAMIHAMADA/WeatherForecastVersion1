package com.rami.weatherforecastversion1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rami.weatherforecastversion1.fragments.MainFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.place_Holder, MainFragment.newInstance()).commit()
    }
}
