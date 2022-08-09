package com.shop.withplanner.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.shop.withplanner.R
import com.shop.withplanner.activity_community.CommunityAuthenticateLocationActivity
import com.shop.withplanner.databinding.DlgMyLocBinding
import android.content.Intent
import android.util.Log
import com.shop.withplanner.activity_community.CommunitySearchLocationActivity
import com.shop.withplanner.dto.CommunityList
import com.shop.withplanner.dto.MyLoc
import com.shop.withplanner.retrofit.RetrofitService
import com.shop.withplanner.shared_preferences.SharedManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.shop.withplanner.dto.Result

class MyLocDialog() : DialogFragment() {
    // dlg_my_loc의 다이얼로그 프래그먼트
    private lateinit var binding : DlgMyLocBinding
    private val sharedManager: SharedManager by lazy { SharedManager(requireContext()) }

    // context 사용을 위한 코드
    var location: String? = "목적지를 입력해주세요."
    val body = HashMap<String, String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.dialog_fullscreen)
        isCancelable = true

        var bundle = arguments

        if(bundle!=null){
            Log.d("위도", bundle!!.getDouble("latitude").toString())

            if(bundle.getString("resultName")!=null){
                location = bundle.getString("resultName")
            }
            else if(bundle.getString("roadAddress")!=null){
                location = bundle.getString("roadAddress")
            }
            else if(bundle.getString("address")!=null){
                location = bundle.getString("address")
            }

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DlgMyLocBinding.inflate(inflater, container, false)

        binding.location.text  = location

        binding.helpBtn.setOnClickListener{
//            val location = binding.location.text.toString().trim()
//            val locationAlias = binding.locationAlias.text.toString().trim()
//
//            if(location.isEmpty()){
//                binding.location.error = "목적지를 설정해주세요."
//            }
//            else if(locationAlias.isEmpty()){
//                binding.locationAlias.error = "별칭을 입력해주세요."
//            }
//            else{
//                val intent = Intent(activity, CommunityAuthenticateLocationActivity::class.java)
//                startActivity(intent)
//                dismiss()
//            }
        }
        // 지도 or 목적지 textView 클릭시 검색 창으로 이동
        binding.addLocBtn.setOnClickListener{
            startActivity(Intent(activity, CommunitySearchLocationActivity::class.java))
        }
        binding.location.setOnClickListener{
            startActivity(Intent(activity,CommunitySearchLocationActivity::class.java))
        }

        // 확인버튼
        binding.makeBtn.setOnClickListener{
            // 서버에 저장 필요
            var locationAlias = binding.locationAlias.text.toString().trim()

            if(location?.isEmpty()!!){
                binding.location.error = "목적지를 설정해주세요."
            }
            else{
                // 내 위치정보 서버로 전송
                var bundle = arguments

                var myLoc: MyLoc
                val longitude = bundle?.getDouble("longitude")
                val latitude = bundle?.getDouble("latitude")
                val roadAddress: String? = bundle?.getString("roadAddress")
                val address: String? = bundle?.getString("address")
                val resultName: String? = bundle?.getString("resultName")
                val communityId: Long = 5   // 이거 앞에서 받아올것

                myLoc = MyLoc(longitude!!, latitude!!, roadAddress, address, locationAlias, resultName)

                Log.d("MYLOC", myLoc.toString())

                RetrofitService.userService.sendMyLoc(sharedManager.getToken(), myLoc, communityId)?.
                enqueue(object:Callback<Result> {
                    override fun onResponse(call: Call<Result>, response: Response<Result>) {
                        if(response.isSuccessful) {
                            val result: Result? = response.body()
                            Log.d("MyLocDialog", result.toString())
                        }
                        else {
                            Log.d("MyLocDialog", "onResponse 실패: " + response.errorBody()?.string()!!)
                            sharedManager.getToken()
                        }
                    }
                    override fun onFailure(call: Call<Result>, t: Throwable) {
                        Log.d("MyLocDialog", "onFailure 에러: " + t.message.toString())
                    }
                })


                body.put("latitude", bundle!!.getString("latitude").toString())

                val intent = Intent(activity, CommunityAuthenticateLocationActivity::class.java)
                startActivity(intent)
                dismiss()
            }

        }
        return binding.root
    }
}