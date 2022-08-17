package com.shop.withplanner.activity_community

import android.Manifest
import android.app.AlertDialog
import android.app.ProgressDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
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
import com.shop.withplanner.activity_etc.CategoryActivity
import com.shop.withplanner.activity_etc.MainActivity
import com.shop.withplanner.databinding.ActivityCommunityCreateBinding
import com.shop.withplanner.dto.MakeCommunity
import com.shop.withplanner.retrofit.RetrofitService

import com.shop.withplanner.util.ImgUtil
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class CommunityCreateActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCommunityCreateBinding
    val checkedDays = booleanArrayOf(false, false, false, false, false, false, false) //체크된 요일
    val dayList = arrayOf<String>("월", "화", "수", "목", "금", "토", "일")
    val checkedItemList= ArrayList<String>() //선택된 요일(항목)을 담는 리스트
    var theNumberList= arrayOf(1,2,3,4,5,6,7,8,9,10)
    var numberOfPerson = 1  // 인원을 담는 변수
    lateinit var imgFile : File
    var authType: String = "post"
    var publicType: String = "publicType"
    val context : Context = this

    private lateinit var sharedPreference: SharedPreferences

    lateinit var progress_Dialog : ProgressDialog
    private var email_dialog : AlertDialog ? = null

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
        sharedPreference = getSharedPreferences("token", MODE_PRIVATE)


        //1. 사진 선택
        checkPermission.launch(permissionList)

        val loadImage = registerForActivityResult(ActivityResultContracts.GetContent(),
            ActivityResultCallback {
                binding.imageText.visibility = View.INVISIBLE
                binding.imageBtn.setImageURI(it)

                // API level 28 이하는 MediaStore.Images.Media.getBitmap 사용 (deprecated)
                // 그 이상부터 ImageDecoder.createSource 사용
                val bitmap = it?.let {
                    if (Build.VERSION.SDK_INT < 28) {
                        MediaStore.Images
                            .Media.getBitmap(context.contentResolver, it)
                    } else {
                        val source = ImageDecoder
                            .createSource(context.contentResolver, it)
                        ImageDecoder.decodeBitmap(source)
                    }
                }

                imgFile = ImgUtil.UriUtil.toFile(context, it!!)
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
                R.id.post_btn -> binding.chooseLocationLinear.visibility = View.GONE
                R.id.location_btn -> authType = "map"
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

        //공개, 비공개 여부 선택
        binding.publicRadioGroup.setOnCheckedChangeListener{group, checkId ->
            when(checkId){
                R.id.public_btn -> publicType = "publicType"
                R.id.private_btn-> publicType = "privateType"
            }}

        //7. 뒤로가기 버튼 클릭시 이벤트
        binding.backBtn.setOnClickListener{
            onBackPressed()
        }

        //8. 인원 선택(linear) 클릭시 이벤트
        binding.theNumberSpinner.adapter=ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item, theNumberList)

        // https://velog.io/@1106laura/Retrofit%EC%97%90%EC%84%9C-Multipart-%EC%84%9C%EB%B2%84-%ED%86%B5%EC%8B%A0-with-Kotlin
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
            val day = binding.dayTextView.text.toString()
            val time = binding.timeTextView.text.toString()+":00"
            val numbOfPerson = numberOfPerson.toString()
            val introduce = binding.introduce.text.toString().trim()
            val publicType = publicType


            if(communityName.isEmpty() || category.isEmpty() || day.isEmpty() || time.isEmpty() ||
                    numbOfPerson.isEmpty() || introduce.isEmpty()) {
                Toast.makeText(this, "빈칸을 모두 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
            else{
                //프로그레스바 보여줌
                getProgressShow()

                val requestFile = RequestBody.create(MediaType.parse("image/*"),  imgFile)
                val imgRequestBody = MultipartBody.Part.createFormData("communityImg", imgFile.name, requestFile)
                val nameRequestBody : RequestBody = communityName.toPlainRequestBody()
                val introduceRequestBody : RequestBody = introduce.toPlainRequestBody()
                val categoryRequestBody : RequestBody = category.toPlainRequestBody()
                val headCountRequestBody : RequestBody = numbOfPerson.toPlainRequestBody()
                val dayRequestBody : RequestBody = day.toPlainRequestBody()
                val timeRequestBody : RequestBody = time.toPlainRequestBody()
                val publicTypeRequestBody : RequestBody = publicType.toPlainRequestBody()

                if(authType.equals("post")) {
                    RetrofitService.communityService.makePostCommunity(sharedPreference.getString("token", null).toString(), imgRequestBody, nameRequestBody, introduceRequestBody, categoryRequestBody, headCountRequestBody, dayRequestBody, timeRequestBody, publicTypeRequestBody)?.enqueue(object :
                        retrofit2.Callback<MakeCommunity> {
                        override fun onResponse(
                            call: Call<MakeCommunity>,
                            response: Response<MakeCommunity>
                        ) {
                            if(response.isSuccessful) {
                                var result : MakeCommunity? = response.body()
                                Log.d("MakeCommunity", "onResponse 성공: " + result?.toString());
                                var intent = Intent(context, CommunityMainPostActivity::class.java)
                                intent.putExtra("communityId", result!!.result.id.toLong())

                                //성공적으로 통신 했으므로 프로그래스 팝업창 닫기
                                getProgressHidden()

                                //생성한 커뮤니티가 비공개 습관 모임일 경우 다이얼로그로 메일 확인하라고 안내
                                if(result!!.result.publicType.toString()=="privateType"){
                                    email_dialog = showDialog2("메일 확인 안내", "비공개 습관 모임의 비밀번호를 메일로 전송했습니다.", result!!.result.id.toLong())
                                    email_dialog!!.show()
                                }
                                else if (result!!.result.publicType.toString()=="publicType"){
                                    var intent = Intent(context, CommunityMainPostActivity::class.java)
                                    intent.putExtra("communityId", result!!.result.id.toLong())
                                    startActivity(intent)
                                    finish()
                                }

                               // startActivity(intent)
                               // finish()
                            } else {
                                Log.d("MakeCommunity", "onResponse 실패");
                            }
                        }

                        override fun onFailure(call: Call<MakeCommunity>, t: Throwable) {
                            Log.d("MakeCommunity", "onFailure 에러: " + t.message.toString());
                        }
                    })

                } else if (authType.equals("map")) {
                    RetrofitService.communityService.makeMapCommunity(sharedPreference.getString("token", null).toString(), imgRequestBody, nameRequestBody, introduceRequestBody, categoryRequestBody, headCountRequestBody, dayRequestBody, timeRequestBody, publicTypeRequestBody)?.enqueue(object :
                        retrofit2.Callback<MakeCommunity> {
                        override fun onResponse(
                            call: Call<MakeCommunity>,
                            response: Response<MakeCommunity>
                        ) {
                            if(response.isSuccessful) {
                                var result : MakeCommunity? = response.body()

                                //성공적으로 통신 했으므로 프로그래스 팝업창 닫기
                                getProgressHidden()

                                //생성한 커뮤니티가 비공개 습관 모임일 경우 다이얼로그로 메일 확인하라고 안내
                                if(result!!.result.publicType.toString()=="privateType"){
                                    email_dialog = showDialog("메일 확인 안내", "비공개 습관 모임의 비밀번호를 메일로 전송했습니다.", result!!.result.id.toLong())
                                    email_dialog!!.show()
                                }
                                else if (result!!.result.publicType.toString()=="publicType"){
                                    var intent = Intent(context, CommunitySearchLocationActivity::class.java)
                                    intent.putExtra("communityId", result!!.result.id.toLong())
                                    startActivity(intent)
                                    finish()
                                }


                                Log.d("MakeCommunity", "onResponse 성공: " + result?.toString());
                                //var intent = Intent(context, CommunitySearchLocationActivity::class.java)
                                //intent.putExtra("communityId", result!!.result.id.toLong())
                                //startActivity(intent)
                               // finish()
                            } else {
                                Log.d("MakeCommunity", "onResponse 실패");
                            }
                        }

                        override fun onFailure(call: Call<MakeCommunity>, t: Throwable) {
                            Log.d("MakeCommunity", "onFailure 에러: " + t.message.toString());
                        }

                    })
                }

            }
        }
        
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun getTime(textview: TextView, context: Context){
        val cal = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener{ timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            textview.text = SimpleDateFormat("HH:mm").format(cal.time)
        }
        var timePickerDialog=TimePickerDialog(context, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false)
        timePickerDialog.show()
    }

    private fun String?.toPlainRequestBody() = RequestBody.create(MediaType.parse("text/plain"), this)

    fun showDialog(titleName: String, message: String, communityId: Long): AlertDialog? {
        val builder = AlertDialog.Builder(this)
            .setTitle(titleName)
            .setMessage(message)
            .setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
                intent = Intent(this@CommunityCreateActivity, CommunitySearchLocationActivity::class.java)
                intent.putExtra("communityId", communityId)
                startActivity(intent)
                finish()
            }).show()
        return builder
    }

    fun showDialog2(titleName: String, message: String, communityId: Long): AlertDialog? {
        val builder = AlertDialog.Builder(this)
            .setTitle(titleName)
            .setMessage(message)
            .setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
                intent = Intent(this@CommunityCreateActivity, CommunityMainPostActivity::class.java)
                intent.putExtra("communityId", communityId)
                startActivity(intent)
                finish()
            }).show()
        return builder
    }




    // 원형 프로그래스 팝업창 호출 부분
    fun getProgressShow(){
        try{
            var str_tittle = "위드플래너"
            var str_message = "커뮤니티를 생성 중입니다. \n" +
                    "잠시만 기다려주세요"
            progress_Dialog = ProgressDialog(this@CommunityCreateActivity)
            progress_Dialog.setTitle(str_tittle) //팝업창 타이틀 지정
            progress_Dialog.setIcon(R.drawable.logo500) //팝업창 아이콘 지정
            progress_Dialog.setMessage(str_message) //팝업창 내용 지정
            progress_Dialog.setCancelable(false) //외부 레이아웃 클릭시도 팝업창이 사라지지않게 설정
            progress_Dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER) //프로그레스 원형 표시 설정
            try {
                progress_Dialog.show()
            }
            catch (e : Exception){
                e.printStackTrace()
            }
        }
        catch(e : Exception){
            e.printStackTrace()
        }
    }
    // 원형 프로그래스 팝업창 닫기 부분
    fun getProgressHidden(){
        try {
            progress_Dialog.dismiss()
            progress_Dialog.cancel()
        }
        catch (e : Exception){
            e.printStackTrace()
        }
    }


}