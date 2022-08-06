package com.shop.withplanner.activity_community

import android.Manifest
import android.app.AlertDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.shop.withplanner.R
import com.shop.withplanner.databinding.ActivityCommunityCreateBinding
import com.shop.withplanner.activity_etc.CategoryActivity
import java.text.SimpleDateFormat
import java.util.*


class CommunityCreateActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCommunityCreateBinding
    val checkedDays =  booleanArrayOf(false,false,false,false,false,false,false) //체크된 요일
    val dayList= arrayOf<String>("월요일", "화요일", "수요일", "목요일", "금요일", "토요일", "일요일")
    val checkedItemList= ArrayList<String>() //선택된 요일(항목)을 담는 리스트
    var theNumberList= arrayOf(1,2,3,4,5,6,7,8,9,10)
    var numberOfPerson = 1  // 인원을 담는 변수

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
    // 카테고리 값 전달을 위한 ActivityResultHandler
    val getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if(result.resultCode == RESULT_OK) {
            val category = result.data?.getStringExtra("category")
            binding.categoryTv.text = category
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
                binding.imageBtn.setImageURI(it)
                Log.d("TAG:", it.toString())
            }
        )
        binding.imageBtn.setOnClickListener(View.OnClickListener {
            loadImage.launch("image/*") })


        //2. 카테고리 선택
        binding.categoryLinear.setOnClickListener{
            val intent = Intent(this, CategoryActivity::class.java)
            getResult.launch(intent)
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

        //8. 인원 선택(linear) 클릭시 이벤트
        binding.theNumberSpinner.adapter=ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item, theNumberList)

        binding.theNumberSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                numberOfPerson = parent?.getItemAtPosition(pos) as Int
            }
        }

        // 완료버튼
        binding.doneBtn.setOnClickListener{
            // 이미지, 위치정보도 저장해야함
            val communityName = binding.communityName.text.toString().trim()
            val category = binding.categoryTv.text.toString()
            var authType: String
            val day = binding.dayTextView.text.toString()
            val time = binding.timeTextView.text.toString()
            val numbOfPerson = numberOfPerson.toString()
            val introduce = binding.introduce.text.toString().trim()

            // 인증방식
            binding.authRadioGroup.setOnCheckedChangeListener{group, checkId ->
                when(checkId){
                    R.id.postAuthBtn -> authType = "post"
                    R.id.locAuthBtn -> authType = "map"
                }}

            if(communityName.isEmpty() || category.isEmpty() || day.isEmpty() || time.isEmpty() ||
                    numbOfPerson.isEmpty() || introduce.isEmpty()) {
                Toast.makeText(this, "빈칸을 모두 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
            else{
                val intent = Intent(this, CommunityMainLocationActivity::class.java)
                startActivity(intent)
                finish()
            }
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