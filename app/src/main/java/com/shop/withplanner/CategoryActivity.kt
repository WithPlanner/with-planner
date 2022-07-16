package com.shop.withplanner

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.shop.withplanner.community.CommunityCreateActivity

class CategoryActivity : AppCompatActivity() {
    private lateinit var binding : com.shop.withplanner.databinding.ActivityCategoryBinding
    private lateinit var category:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_category)

        val myIntent = Intent(this, CommunityCreateActivity::class.java)

        //카테고리 버튼
        binding.categoryBtn1.setOnClickListener{
            category=binding.categoryBtn1.getText().toString()
        }
        binding.categoryBtn2.setOnClickListener{
            category=binding.categoryBtn2.getText().toString()
        }
        binding.categoryBtn3.setOnClickListener{
            category=binding.categoryBtn3.getText().toString()
        }
        binding.categoryBtn4.setOnClickListener{
            category=binding.categoryBtn4.getText().toString()
        }
        binding.categoryBtn5.setOnClickListener{
            category=binding.categoryBtn5.getText().toString()
        }
        binding.categoryBtn6.setOnClickListener{
            category=binding.categoryBtn6.getText().toString()
        }
        binding.categoryBtn7.setOnClickListener{
            category=binding.categoryBtn7.getText().toString()

        }
        binding.categoryBtn8.setOnClickListener{
            category=binding.categoryBtn8.getText().toString()
        }
        binding.categoryBtn9.setOnClickListener{
            category=binding.categoryBtn9.getText().toString()
        }
        binding.categoryBtn10.setOnClickListener{
            category=binding.categoryBtn10.getText().toString()
        }
        binding.categoryBtn11.setOnClickListener{
            category=binding.categoryBtn11.getText().toString()
        }
        binding.categoryBtn12.setOnClickListener{
            category=binding.categoryBtn12.getText().toString()
        }
        binding.categoryBtn13.setOnClickListener{
            category=binding.categoryBtn13.getText().toString()
        }
        binding.categoryBtn14.setOnClickListener{
            category=binding.categoryBtn14.getText().toString()
        }
        binding.categoryBtn15.setOnClickListener{
            category=binding.categoryBtn15.getText().toString()
        }
        binding.categoryBtn16.setOnClickListener{
            category=binding.categoryBtn16.getText().toString()
        }
        binding.categoryBtn17.setOnClickListener{
            category=binding.categoryBtn17.getText().toString()
        }

        //뒤로가기 버튼
        binding.backBtn.setOnClickListener{
            onBackPressed()
        }
        binding.okBtn.setOnClickListener{
            myIntent.putExtra("category",category)
            startActivity(myIntent)
        }


    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

}