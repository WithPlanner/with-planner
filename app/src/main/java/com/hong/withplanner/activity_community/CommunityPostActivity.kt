package com.hong.withplanner.activity_community

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.hong.withplanner.R
import com.hong.withplanner.databinding.ActivityCommunityPostBinding

class CommunityPostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCommunityPostBinding
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
                val intent = Intent(this, CommunityMainPostActivity::class.java)

                // 인증확인 다이얼로그
                val builder = AlertDialog.Builder(this).setTitle(habitName)
                    .setMessage("오늘의 ${habitName} 인증을 완료했습니다.")
                    .setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
                        startActivity(intent)
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