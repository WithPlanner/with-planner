package com.shop.withplanner.activity_community

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.shop.withplanner.R
import com.shop.withplanner.activity_etc.MainActivity
import com.shop.withplanner.databinding.ActivityCommunityPostBinding
import com.shop.withplanner.dto.CommunityPostMain
import com.shop.withplanner.dto.IdAndMsg
import com.shop.withplanner.dto.IdAndMsgResult
import com.shop.withplanner.dto.MakeCommunity
import com.shop.withplanner.retrofit.RetrofitService
import com.shop.withplanner.shared_preferences.SharedManager
import com.shop.withplanner.util.ImgUtil
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import java.io.File

class CommunityPostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCommunityPostBinding
    val context : Context = this
    private val sharedManager: SharedManager by lazy { SharedManager(this) }
    lateinit var imgFile : File
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

        var communityId = intent.getLongExtra("communityId", -1L)

        // 권한 확인
        checkPermission.launch(permissionList)
        // 사진 선택
        val loadImage = registerForActivityResult(ActivityResultContracts.GetContent(),
            ActivityResultCallback {
                binding.imageArea.visibility = View.VISIBLE
                binding.imageArea.setImageURI(it)

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
            })

        binding.imageBtn.setOnClickListener(View.OnClickListener {
            loadImage.launch("image/*") })


        // 완료 버튼
        binding.doneBtn.setOnClickListener{
            // POST할 것
            val title = binding.title.text.toString().trim()
            val content = binding.content.text.toString().trim()

            // 습관이름
            var category = ""
            category = intent.getStringExtra("category").toString()


            // 게시물 내용물이 모두 입력되었다면 서버 전송
            if(title.isEmpty() || content.isEmpty()) {
                Toast.makeText(this, "빈칸을 모두 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
            else {
                val requestFile = RequestBody.create(MediaType.parse("image/*"), imgFile)
                val imgRequestBody =
                    MultipartBody.Part.createFormData("img", imgFile.name, requestFile)
                val nameRequestBody: RequestBody = title.toPlainRequestBody()
                val contentRequestBody: RequestBody = content.toPlainRequestBody()

                RetrofitService.postService.writePost(sharedManager.getToken(), communityId, imgRequestBody, nameRequestBody, contentRequestBody)?.enqueue(object :
                retrofit2.Callback<IdAndMsg> {
                    override fun onResponse(call: Call<IdAndMsg>, response: Response<IdAndMsg>) {
                        if(response.isSuccessful) {
                            var result : IdAndMsgResult = response.body()!!.result

                            if(response.body()!!.code == 1000) {

                                val builder = AlertDialog.Builder(this@CommunityPostActivity).setTitle(category)
                                    .setMessage("오늘의 ${category} 인증을 완료했습니다.")
                                    .setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
                                        var intent = Intent(this@CommunityPostActivity, CommunityPostBoardActivity::class.java)
                                        intent.putExtra("communityId", communityId)
                                        intent.putExtra("category", category)
                                        intent.putExtra("communityType", "post")
                                        startActivity(intent)
                                        finish()
                                    }).show()
                            }
                            else{
                                showDialog(category, "${response.body()!!.code}: 서버 전송에 실패했습니다.")
                            }
                        } else {
                            Log.d("WritePost", "onResponse 실패: "+response.errorBody()?.string()!!)
                        }
                    }
                    override fun onFailure(call: Call<IdAndMsg>, t: Throwable) {
                        Log.d("WritePost", "onFailure 에러: " + t.message.toString());
                    }
                })
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
    private fun String?.toPlainRequestBody() = RequestBody.create(MediaType.parse("text/plain"), this!!)

    fun showDialog(titleName: String, message: String) {
        val builder = AlertDialog.Builder(this).setTitle(titleName)
            .setMessage(message)
            .setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
            }).show()
    }
}
