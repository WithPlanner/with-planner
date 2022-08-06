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
import android.content.Intent.getIntent
import android.util.Log
import com.shop.withplanner.activity_community.CommunitySearchLocationActivity

class MyLocDialog() : DialogFragment() {
    // dlg_my_loc의 다이얼로그 프래그먼트
    private lateinit var binding : DlgMyLocBinding
    var location: String? = "목적지를 입력해주세요."

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
            else if(bundle.getString("roadAddress")!=null){
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
            // 서버에 저장 필요
            val location = binding.location.text.toString().trim()
            val locationAlias = binding.locationAlias.text.toString().trim()

            if(location.isEmpty()){
                binding.location.error = "목적지를 설정해주세요."
            }
            else if(locationAlias.isEmpty()){
                binding.locationAlias.error = "별칭을 입력해주세요."
            }
            else{
                val intent = Intent(activity, CommunityAuthenticateLocationActivity::class.java)
                startActivity(intent)
                dismiss()
            }
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
            val location = binding.location.text.toString().trim()
            val locationAlias = binding.locationAlias.text.toString().trim()

            if(location.isEmpty()){
                binding.location.error = "목적지를 설정해주세요."
            }
            else if(locationAlias.isEmpty()){
                binding.locationAlias.error = "별칭을 입력해주세요."
            }
            else{
                val intent = Intent(activity, CommunityAuthenticateLocationActivity::class.java)
                startActivity(intent)
                dismiss()
            }

        }
        return binding.root
    }
}