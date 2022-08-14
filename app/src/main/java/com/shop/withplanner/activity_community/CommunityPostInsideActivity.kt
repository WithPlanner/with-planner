package com.shop.withplanner.activity_community

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.shop.withplanner.R
import com.shop.withplanner.databinding.ActivityCommunityPostInsideBinding
import com.shop.withplanner.dto.CommentResponse
import com.shop.withplanner.dto.MapPostDetail
import com.shop.withplanner.dto.PostDetail
import com.shop.withplanner.recyler_view.*
import com.shop.withplanner.retrofit.RetrofitService
import com.shop.withplanner.shared_preferences.SharedManager
import org.w3c.dom.Comment
import retrofit2.Call
import retrofit2.Response

class CommunityPostInsideActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCommunityPostInsideBinding
    private lateinit var sharedPreference: SharedPreferences
    private val commentItems = mutableListOf<CommentModel>()
    val commentLVAdapter = CommentAdapter(commentItems)

    var postId: Int = -1
    var communityId: Long = -1L
    var postType = ""
    var category = ""
    val body = HashMap<String, String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_community_post_inside)
        sharedPreference = getSharedPreferences("token", MODE_PRIVATE)

        // 게시물 정보 불러오기
        postId = intent.getIntExtra("postId", -1)
        communityId = intent.getLongExtra("communityId", -1L)
        postType = intent.getStringExtra("postType").toString()
        category = intent.getStringExtra("category").toString()

        if(postType == "mapPost") {
            RetrofitService.postService.getMapPostDetail(sharedPreference.getString("token", null).toString(), postId.toLong()).enqueue(
                object : retrofit2.Callback<MapPostDetail> {
                    override fun onResponse(call: Call<MapPostDetail>, response: Response<MapPostDetail>) {
                        if(response.isSuccessful) {
                            var result = response.body()!!.result

                            if(response.body()!!.code == 1000) {
                                Log.d("sendComment", "onResponse 성공 $result")

                                binding.nickname.text = result.nickName
                                binding.date.text = result.updatedAt
                                binding.habbit.text = category
                                val location = result.location
                                binding.content.text = "${location}에서 오늘의 습관을 완료했어요!"

                                binding.image.visibility = View.GONE

                                // 댓글
                                for(comment in result.comments) {
                                    commentItems.add(
                                        CommentModel(comment.nickname, comment.comment
                                        ))
                                }
                                binding.commentLV.adapter = commentLVAdapter
                            }

                            else{
                                Log.d("sendComment", "onResponse 실패 ${response.body()!!.message}")
                            }
                        } else {
                            Log.d("sendComment", "onResponse 실패");
                        }
                    }
                    override fun onFailure(call: Call<MapPostDetail>, t: Throwable) {
                        Log.d("sendComment", "onFailure 에러: " + t.message.toString());
                    }

                }
            )
        }
        else if(postType == "post") {
            RetrofitService.postService.getPostDetail(sharedPreference.getString("token", null).toString(), postId.toLong()).enqueue(
                object : retrofit2.Callback<PostDetail> {
                    override fun onResponse(call: Call<PostDetail>, response: Response<PostDetail>) {
                        if(response.isSuccessful) {
                            var result = response.body()!!.result

                            if(response.body()!!.code == 1000) {
                                Log.d("sendComment", "onResponse 성공 $result")

                                binding.nickname.text = result.writerNickname
                                binding.date.text = result.updatedAt
                                binding.habbit.text = result.name
                                binding.content.text = result.content

                                // 게시글 이미지
                                binding.image.visibility = View.VISIBLE
                                Glide.with(this@CommunityPostInsideActivity)
                                    .load(result.images[0].imgUrl).into(binding.image)

                                // 댓글
                                for(comment in result.comments) {
                                    commentItems.add(
                                        CommentModel(comment.nickname, comment.comment
                                        ))
                                }
                                binding.commentLV.adapter = commentLVAdapter
                            }

                            else{
                                Log.d("sendComment", "onResponse 실패 ${response.body()!!.message}")
                            }
                        } else {
                            Log.d("sendComment", "onResponse 실패");
                        }
                    }
                    override fun onFailure(call: Call<PostDetail>, t: Throwable) {
                        Log.d("sendComment", "onFailure 에러: " + t.message.toString());
                    }
                }
            )
        }

        // 댓글 입력
        binding.commentBtn.setOnClickListener{
            body.put("comment", binding.comment.text.toString().trim())

            RetrofitService.commentService.sendComment(sharedPreference.getString("token", null).toString(), communityId, postId, body).enqueue(
                object : retrofit2.Callback<CommentResponse> {
                    override fun onResponse(call: Call<CommentResponse>, response: Response<CommentResponse>) {
                        if(response.isSuccessful) {

                            var result = response.body()!!.result
                            Log.d("sendComment", "onResponse 성공 $result")

                            // 리프레쉬
                            commentItems.add(CommentModel(result.nickname, result.comment))
                            commentLVAdapter.notifyDataSetChanged()

                            binding.comment.text = null
                            softkeyboardHide()


                        } else {
                            Log.d("sendComment", "onResponse 실패");
                        }
                    }
                    override fun onFailure(call: Call<CommentResponse>, t: Throwable) {
                        Log.d("sendComment", "onFailure 에러: " + t.message.toString());
                    }
                }
            )
        }

        binding.backBtn.setOnClickListener{
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    // 댓글창 외에 다른곳을 누르면 소프트키보드 내려감
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        return true
    }

    // 키보드 내리기
    fun softkeyboardHide() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.comment.windowToken, 0)
    }
}