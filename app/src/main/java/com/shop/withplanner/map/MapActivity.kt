package com.shop.withplanner.map

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.shop.withplanner.R
import com.shop.withplanner.databinding.ActivityMapBinding
import net.daum.mf.map.api.MapView;

class MapActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMapBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_map)

        val mapView = MapView(this)
        binding.mapView.addView(mapView)
    }

}