package com.shop.withplanner.activity_etc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.shop.withplanner.R
import com.shop.withplanner.databinding.ActivitySettingBinding
import com.shop.withplanner.recyler_view.SettingAdapter
import com.shop.withplanner.recyler_view.SettingModel
import com.shop.withplanner.shared_preferences.SharedManager

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding
    private val sharedManager: SharedManager by lazy { SharedManager(this) }
    val functionList = mutableListOf<SettingModel>()
    val functionName = listOf<String>("닉네임 변경", "비밀번호 변경", "알림 설정", "로그아웃")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting)

        for(name in functionName) {
            functionList.add(SettingModel(name))
        }

        val settingAdapter = SettingAdapter(functionList)
        binding.functionLV.adapter = settingAdapter

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        // 리스트뷰
        binding.functionLV.setOnItemClickListener{ parent, view, position, id ->
            when(functionName[position]) {
                 "로그아웃" -> {
                     val intent = Intent(this, LoginActivity::class.java)
                     intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                     startActivity(intent)
                 }
            }
        }

    }
    override fun onBackPressed() {
        super.onBackPressed()
    }
}