package com.hong.withplanner.community

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
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.hong.withplanner.CategoryActivity
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community_create)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_community_create)

        //1. 요일 선택 (linear) 클릭시 이벤트
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
        //2. 시간 선택(linear) 클릭시 이벤트
        binding.chooseTimeLinear.setOnClickListener {
            getTime(binding.timeTextView,this)
        }
        //3. 위치 선택(linear) 클릭시 이벤트
        binding.chooseLocationLinear.setOnClickListener {
            startActivity(Intent(this, CommunitySearchLocationActivity::class.java))
        }
        //4. 뒤로가기 버튼 클릭시 이벤트
        binding.backBtn.setOnClickListener{
            onBackPressed()
        }

        //인증방식 라디오 버튼 클릭 시
        binding.authRadioGroup.setOnCheckedChangeListener{group, checkId ->
            when(checkId){
                R.id.authRadioBtn1 -> binding.chooseLocationLinear.visibility = View.GONE
                R.id.authRadioBtn2 -> binding.chooseLocationLinear.visibility=View.VISIBLE
            }}

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
        binding.categoryLinear.setOnClickListener{
            startActivity(Intent(this, CategoryActivity::class.java))
        }
        if(intent.hasExtra("category")){
            category= intent.getStringExtra("category").toString()
            println(category)
            binding.categoryTv.text = category
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

}}