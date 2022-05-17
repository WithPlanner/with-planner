package com.hong.withplanner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hong.withplanner.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private val items =  mutableListOf<ContentsModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        items.add(
            ContentsModel(
                "구움양과 by 런던케이크",
                "https://mp-seoul-image-production-s3.mangoplate.com/46651_1630510033594478.jpg?fit=around|512:512&crop=512:512;*,*&output-format=jpg&output-quality=80",
            )
        )
        items.add(
            ContentsModel(
                "구움양과 by 런던케이크",
                "https://mp-seoul-image-production-s3.mangoplate.com/46651_1630510033594478.jpg?fit=around|512:512&crop=512:512;*,*&output-format=jpg&output-quality=80",
            )
        )
        items.add(
            ContentsModel(
                "구움양과 by 런던케이크",
                "https://mp-seoul-image-production-s3.mangoplate.com/46651_1630510033594478.jpg?fit=around|512:512&crop=512:512;*,*&output-format=jpg&output-quality=80",
            )
        )

        val rv = binding.recyclerView1
        val rvAdapter = RVAdapter(this ,items)
        rv.adapter = rvAdapter

        rvAdapter.itemClick = object : RVAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
//                val intent = Intent(this@MainActivity, MyCalendarActivity::class.java)
                val intent = Intent(this@MainActivity, CommunitySelectActivity::class.java)
                startActivity(intent)
            }
        }

        rv.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)

    }
}