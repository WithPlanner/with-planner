package com.shop.withplanner.activity_community

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.shop.withplanner.R
import com.shop.withplanner.databinding.ActivityCommunityPostBinding
import com.shop.withplanner.dto.CommunityPostMain
import com.shop.withplanner.retrofit.RetrofitService
import com.shop.withplanner.shared_preferences.SharedManager
import retrofit2.Call
import retrofit2.Response

class CommunityPostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCommunityPostBinding
    private val sharedManager: SharedManager by lazy { SharedManager(this) }
    // 공용저장소 권한 확인
    private val permissionList = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
    private val checkPermission = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
        result.forEach {
            if (!it.value) {
                Toast.makeText(applicationContext, "권한 동의가 필요합니다.", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_community_post)

        // 권한 확인
        checkPermission.launch(permissionList)
        // 사진 선택
        val loadImage = registerForActivityResult(ActivityResultContracts.GetContent(),
            ActivityResultCallback {
                binding.imageArea.visibility = View.VISIBLE
                binding.imageArea.setImageURI(it)
            })
        binding.imageBtn.setOnClickListener(View.OnClickListener {
            loadImage.launch("image/*") })


        // 완료 버튼
        binding.doneBtn.setOnClickListener{
            // POST할 것
            val title = binding.title.text.toString().trim()
            val content = binding.content.text.toString().trim()
            val currentTimestamp = System.currentTimeMillis()

            // GET할 것
            val habitName = "습관이름"

            // 유저정보, 이미지 받아와서 저장 필요

            if(title.isEmpty()){
                Toast.makeText(this, "제목을 입력하세요.", Toast.LENGTH_SHORT).show()
            }
            else if(content.isEmpty()) {
                Toast.makeText(this, "내용을 입력하세요.", Toast.LENGTH_SHORT).show()
            }
            else{

                // 인증확인 다이얼로그
                val builder = AlertDialog.Builder(this).setTitle(habitName)
                    .setMessage("오늘의 ${habitName} 인증을 완료했습니다.")
                    .setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
                        finish()
                    }).show()
            }
        }

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK && requestCode == 100) {
            binding.imageArea.setImageURI(data?.data)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}