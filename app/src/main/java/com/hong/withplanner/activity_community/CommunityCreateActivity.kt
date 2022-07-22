package com.hong.withplanner.activity_community

import android.Manifest
import android.app.AlertDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.hong.withplanner.activity_etc.CategoryActivity
import com.hong.withplanner.R
import com.hong.withplanner.databinding.ActivityCommunityCreateBinding
import java.text.SimpleDateFormat
import java.util.*


class CommunityCreateActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCommunityCreateBinding
    val checkedDays =  booleanArrayOf(false,false,false,false,false,false,false) //체크된 요일
    val dayList= arrayOf<String>("월요일", "화요일", "수요일", "목요일", "금요일", "토요일", "일요일")
    val checkedItemList= ArrayList<String>() //선택된 요일(항목)을 담는 리스트
    var theNumberList= arrayOf(1,2,3,4,5,6,7,8,9,10)
    var category = " " //카테고리

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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_community_create)

        //1. 사진 선택
        checkPermission.launch(permissionList)

        val loadImage = registerForActivityResult(ActivityResultContracts.GetContent(),
            ActivityResultCallback {
                binding.imageText.visibility = View.INVISIBLE
                binding.imageBtn.setImageURI(it) }
        )
        binding.imageBtn.setOnClickListener(View.OnClickListener {
            loadImage.launch("image/*") })


        //2. 카테고리 선택
        binding.categoryLinear.setOnClickListener{
            startActivity(Intent(this, CategoryActivity::class.java))
        }
        // 카테고리를 받아왔다면 바인딩
        if(intent.hasExtra("category")){
            category= intent.getStringExtra("category").toString()
            binding.categoryTv.text = category
        }


        //3. 인증방식
        binding.authRadioGroup.setOnCheckedChangeListener{group, checkId ->
            when(checkId){
                R.id.postAuthBtn -> binding.chooseLocationLinear.visibility = View.GONE
                R.id.locAuthBtn -> binding.chooseLocationLinear.visibility = View.VISIBLE
            }}


        //4. 요일 선택 (linear) 클릭시 이벤트
        binding.chooseDayLinear.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("요일 선택")    // 제목

            // 항목 클릭 시 이벤트
            builder.setMultiChoiceItems(dayList, checkedDays) { dialog, which, isChecked ->
                // 체크 시 리스트에 추가
                if (isChecked) {
                    checkedItemList.add(dayList[which])
                }
                // 체크 해제 시 리스트에서 제거
                else if (checkedItemList.contains(dayList[which])) {
                    checkedItemList.remove(dayList[which])
                }
            }
            // 확인 버튼
            builder.setPositiveButton("확인") { dialog, which ->
                binding.dayTextView.text = checkedItemList.joinToString(", ")
            }
            // 취소 버튼
            builder.setNegativeButton("취소") { dialog, which ->
                binding.dayTextView.text = ""
            }
            builder.show()
        }


        //5. 시간 선택(linear) 클릭시 이벤트
        binding.chooseTimeLinear.setOnClickListener {
            getTime(binding.timeTextView,this)
        }


        //6. 위치 선택(linear) 클릭시 이벤트
        binding.chooseLocationLinear.setOnClickListener {
            startActivity(Intent(this, CommunitySearchLocationActivity::class.java))
        }


        //7. 뒤로가기 버튼 클릭시 이벤트
        binding.backBtn.setOnClickListener{
            onBackPressed()
        }


        binding.theNumberSpinner.adapter=ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item, theNumberList)

        binding.theNumberSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                /* 이런식으로 작성.
                when (position){
                0->{}
                1->{}
                2->{}
                ...
                else->{}
                }
                * */
            }
        }

        // 완료버튼
        binding.doneBtn.setOnClickListener{
            val communityName = binding.communityName.text.toString().trim()
            val category = binding.categoryTv.text.toString()
            val introduce = binding.introduce.text.toString().trim()
            var authType: String

            binding.authRadioGroup.setOnCheckedChangeListener{group, checkId ->
                when(checkId){
                    R.id.postAuthBtn -> authType = "post"
                    R.id.locAuthBtn -> authType = "map"
                }}


            val intent = Intent(this, CommunityMainLocationActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun getTime(textview: TextView, context: Context){
        val cal = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            textview.text = SimpleDateFormat("HH시 mm분").format(cal.time)
        }
        var timePickerDialog=TimePickerDialog(context, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true)
        timePickerDialog.setButton(TimePickerDialog.BUTTON_POSITIVE,"확인",DialogInterface.OnClickListener{timePickerDialog,which ->textview.text = SimpleDateFormat("HH시 mm분").format(cal.time) })
        timePickerDialog.setButton(TimePickerDialog.BUTTON_NEGATIVE,"취소",DialogInterface.OnClickListener{timePickerDialog,which ->textview.text = " " })
        timePickerDialog.show()
    }
}