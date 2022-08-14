package com.shop.withplanner.dialog

import android.content.Context.MODE_PRIVATE
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.shop.withplanner.R
import com.shop.withplanner.activity_community.CommunityAuthenticateLocationActivity
import com.shop.withplanner.databinding.DlgMyLocBinding
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.shop.withplanner.activity_community.CommunityMainLocationActivity
import com.shop.withplanner.activity_community.CommunitySearchLocationActivity
import com.shop.withplanner.activity_etc.MainActivity
import com.shop.withplanner.dto.MyLocReceived
import com.shop.withplanner.dto.MyLocToSend
import com.shop.withplanner.dto.MyLocToSendResponse
import com.shop.withplanner.retrofit.RetrofitService
import com.shop.withplanner.shared_preferences.SharedManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.shop.withplanner.dto.Result

class MyLocDialog() : DialogFragment() {
    // dlg_my_loc의 다이얼로그 프래그먼트
    private lateinit var binding : DlgMyLocBinding
    private lateinit var sharedPreference: SharedPreferences

    // context 사용을 위한 코드
    var location: String? = "목적지를 입력해주세요."
    var communityId: Long? = -1L
    val body = HashMap<String, String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.dialog_fullscreen)
        sharedPreference = requireContext().getSharedPreferences("token", MODE_PRIVATE)
        isCancelable = true

        var bundle = arguments

        communityId = bundle?.getLong("communityId", -1L)

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

        }

        // 지도 or 목적지 textView 클릭시 검색 창으로 이동
        val intent = Intent(requireContext(), CommunitySearchLocationActivity::class.java)
        intent.putExtra("communityId", communityId)

        binding.addLocBtn.setOnClickListener{
            startActivity(intent)
        }
        binding.location.setOnClickListener{
            startActivity(intent)
        }

        // 확인버튼
        binding.makeBtn.setOnClickListener{
            if(location?.isEmpty()!!){
                binding.location.error = "목적지를 설정해주세요."
            }
            else{
                // 내 위치정보 서버로 전송
                var bundle = arguments
                var locationAlias = ""

                // 목적지 별칭이 있으면 별칭으로, 없으면 주소로
                if(binding.locationAlias.text.toString().trim() != "") {
                    locationAlias = binding.locationAlias.text.toString().trim()
                }
                else{
                    locationAlias = location.toString()
                }

                var myLocToSend: MyLocToSend
                val longitude = bundle?.getDouble("longitude")
                val latitude = bundle?.getDouble("latitude")
                val roadAddress: String? = bundle?.getString("roadAddress")
                val address: String? = bundle?.getString("address")
                val resultName: String? = bundle?.getString("resultName")

                myLocToSend = MyLocToSend(longitude!!, latitude!!, roadAddress, address, locationAlias, resultName)

                Log.d("MYLOCTOSEND", myLocToSend.toString())

                RetrofitService.locationService.sendMyLoc(sharedPreference.getString("token", null).toString(), myLocToSend, communityId!!).
                enqueue(object:Callback<MyLocToSendResponse> {
                    override fun onResponse(call: Call<MyLocToSendResponse>, response: Response<MyLocToSendResponse>) {
                        if(response.isSuccessful) {

                            val result = response.body()!!

                            when (result.code) {
                                1000 -> {
                                    Toast.makeText(requireContext(), "목적지 설정 완료", Toast.LENGTH_SHORT).show()

                                    val intent = Intent(activity, CommunityMainLocationActivity::class.java)
                                    intent.putExtra("communityId", communityId)
                                    startActivity(intent)
                                    dismiss()
                                }
                                2008 -> {
                                    binding.errorText.visibility = View.VISIBLE
                                    binding.errorText.text = "${result.code}: 존재하지 않는 커뮤니티입니다."
                                }
                                else -> {
                                    Log.d("MyLocDialog", result.toString())
                                }
                            }
                        }
                        else {
                            Log.d("MyLocDialog", "onResponse 실패: " + response.errorBody()?.string()!!)
                            binding.errorText.visibility = View.VISIBLE
                            binding.errorText.text = "서버 전송에 실패했습니다."
                        }
                    }
                    override fun onFailure(call: Call<MyLocToSendResponse>, t: Throwable) {
                        Log.d("MyLocDialog", "onFailure 에러: " + t.message.toString())
                        binding.errorText.visibility = View.VISIBLE
                        binding.errorText.text = "통신 오류가 발생했습니다."
                    }
                })

//                body.put("latitude", bundle!!.getString("latitude").toString())

            }

        }
        return binding.root
    }

}