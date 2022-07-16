package com.shop.withplanner.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.shop.withplanner.R
import com.shop.withplanner.community.CommunityAuthenticateLocationActivity
import com.shop.withplanner.databinding.DlgMyLocBinding
import android.content.Intent

class MyLocDialog() : DialogFragment() {
    // dlg_my_loc의 다이얼로그 프래그먼트
    private lateinit var binding : DlgMyLocBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.dialog_fullscreen)
        isCancelable = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DlgMyLocBinding.inflate(inflater, container, false)

        // 지도 버튼
        binding.addLocBtn.setOnClickListener{

        }

        // 확인버튼
        binding.makeBtn.setOnClickListener{
            // 서버에 저장 필요
            val location = binding.location.text.toString().trim()
            val locationAlias = binding.locationAlias.text.toString().trim()

            if(location.isEmpty()){
                binding.location.error = "목적지를 설정해주세요."
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