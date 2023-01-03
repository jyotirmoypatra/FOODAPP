package com.example.foodorder.UserActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.viewpager.widget.ViewPager
import com.example.foodorder.Adapter.viewPagerAdapter
import com.example.foodorder.Common.cms
import com.example.foodorder.MainActivity
import com.example.foodorder.R
import com.google.android.material.tabs.TabLayout

class userProfileActivity : AppCompatActivity() {
    lateinit var backBtn:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)



        val viewPager = findViewById<ViewPager>(R.id.viewPager)
        viewPager.adapter = viewPagerAdapter(supportFragmentManager)
        val pos = intent.getStringExtra("pos")
        if(cms.boolOrderSuccess) {
            cms.boolOrderSuccess=false
            viewPager.setCurrentItem(1)
        }

        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        tabLayout.setupWithViewPager(viewPager)

        backBtn=findViewById(R.id.backBtn)
        backBtn.setOnClickListener {


            super.onBackPressed()
        }

    }
}