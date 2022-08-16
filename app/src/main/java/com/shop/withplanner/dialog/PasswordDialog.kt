//package com.shop.withplanner.dialog
//
//
//import android.content.Context.MODE_PRIVATE
//import android.content.Intent
//import android.content.SharedPreferences
//import android.os.Bundle
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.DialogFragment
//import com.shop.withplanner.R
//import com.shop.withplanner.activity_community.CommunityJoinActivity
//import com.shop.withplanner.databinding.DlgPasswordBinding
//
//class PasswordDialog : DialogFragment() {
//    private lateinit var binding : DlgPasswordBinding
//    private lateinit var sharedPreference: SharedPreferences
//    lateinit var password: String
//    var inputPassword: String? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        var bundle = arguments
//        password= bundle?.getString("password").toString()
//        Log.d("password", password)
//    }
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        binding = DlgPasswordBinding.inflate(inflater, container, false)
//
//        binding.okBtn.setOnClickListener {
//            inputPassword = binding.password.text.toString().trim()
//            Log.d("inputpass", inputPassword.toString())
//            if(inputPassword == "") {
//                binding.guideText.text = "비밀번호를 입력하지 않았습니다."
//            }
//            else if(password != inputPassword) {
//                binding.guideText.text = "비밀번호가 틀렸습니다."
//            }
//            else if(password == inputPassword) {
//                // 비밀번호가 맞았다는걸 조인액티비티에 전달
//                sharedPreference = requireContext().getSharedPreferences("password", MODE_PRIVATE)
//                val editor  : SharedPreferences.Editor = sharedPreference.edit()
//                editor.putBoolean("isPasswordCorrect", true)
//                editor.commit()
//
//                startActivity(Intent(requireContext(), CommunityJoinActivity::class.java))
//                dismiss()
//            }
//        }
//        binding.cancelBtn.setOnClickListener {
//            dismiss()
//        }
//
//
//
//        return binding.root
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//    }
//}