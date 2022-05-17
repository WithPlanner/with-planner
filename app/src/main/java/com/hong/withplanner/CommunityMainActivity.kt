package com.hong.withplanner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.PopupMenu
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.hong.withplanner.databinding.ActivityMainCommunityBinding

class CommunityMainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainCommunityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_community)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_community)

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        binding.menuBtn.setOnClickListener {
            var menu = PopupMenu(applicationContext, it)
            menuInflater.inflate(R.menu.community_option_menu, menu.menu)
            menu.show()
            menu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.authMenu -> {
                        startActivity(Intent(this, CommunityMainBoardActivity::class.java))
                        return@setOnMenuItemClickListener true
                    }
                    R.id.talkMenu -> {
                        startActivity(Intent(this, CommunityTalkBoardActivity::class.java))
                        return@setOnMenuItemClickListener true
                    }
                    R.id.infoMenu -> {
                        startActivity(Intent(this, CommunityInfoBoardActivity::class.java))
                        return@setOnMenuItemClickListener true
                    }
                }
                return@setOnMenuItemClickListener false
            }

        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }


}