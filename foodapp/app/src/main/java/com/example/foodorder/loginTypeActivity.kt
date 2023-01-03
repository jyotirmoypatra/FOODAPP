package com.example.foodorder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.foodorder.AdminActivity.AdminLoginActivity
import com.example.foodorder.UserActivity.userLoginActivity

class loginTypeActivity : AppCompatActivity() {
    lateinit var userLog:TextView
    lateinit var resLog:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_type)

        supportActionBar?.hide()
        userLog=findViewById(R.id.userLog)
        resLog=findViewById(R.id.resLog)

        userLog.setOnClickListener {
            val intent = Intent(this, userLoginActivity::class.java)
            startActivity(intent)

        }
        resLog.setOnClickListener {
            val intent = Intent(this, AdminLoginActivity::class.java)
            startActivity(intent)

        }
    }
}