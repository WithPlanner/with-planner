package com.hong.withplanner.community

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hong.withplanner.ContentsModel
import com.hong.withplanner.R
import com.hong.withplanner.RVAdapter
import com.hong.withplanner.databinding.ActivityCommunityCalendarBinding

class CommunityCalendarActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCommunityCalendarBinding
    private val items =  mutableListOf<ContentsModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community_calendar)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_community_calendar)
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

        val rv = binding.recyclerView
        val rvAdapter = RVAdapter(this ,items)
        rv.adapter = rvAdapter

        rvAdapter.itemClick = object : RVAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
            }
        }

        rv.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
    }
}