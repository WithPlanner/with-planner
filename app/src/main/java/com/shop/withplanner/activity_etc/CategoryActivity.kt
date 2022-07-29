package com.shop.withplanner.activity_etc

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.shop.withplanner.R
import com.shop.withplanner.activity_community.CommunityCreateActivity
import com.shop.withplanner.recyler_view.CategoryAdapter
import com.shop.withplanner.recyler_view.CategoryModel

class CategoryActivity : AppCompatActivity() {
    private lateinit var binding : com.shop.withplanner.databinding.ActivityCategoryBinding
    lateinit var category: String
    val categoryList = mutableListOf<CategoryModel>()
    val categoryNameList = listOf("미라클 모닝", "관계 형성(커뮤니케이션)", "디지털 디톡스", "멘탈 관리", "운동",
        "홈 트레이닝", "스트레칭", "시간 관리", "취미 생활", "다이어트", "외국어 공부", "글쓰기 연습 및 필사",
        "나이트 루틴", "집 정돈", "취업 준비", "건강 습관 형성", "독서")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_category)

        val intent = Intent(this, CommunityCreateActivity::class.java)

        // 리스트뷰
        for(name in categoryNameList) {
            categoryList.add(CategoryModel(name))
        }

        val categoryLVAdapter = CategoryAdapter(categoryList)
        binding.categoryLV.adapter = categoryLVAdapter

        // 카테고리 선택시 해당 카테고리 이름 저장
        binding.categoryLV.setOnItemClickListener { parent, view, position, id ->
            category = categoryList[position].category_name
        }


        //뒤로가기 버튼
        binding.backBtn.setOnClickListener{
            onBackPressed()
        }


        // 완료버튼
        binding.okBtn.setOnClickListener{
            intent.putExtra("category", category)   // 이전 activity에 값 전달
            setResult(RESULT_OK, intent)
            if(!isFinishing) finish()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

}