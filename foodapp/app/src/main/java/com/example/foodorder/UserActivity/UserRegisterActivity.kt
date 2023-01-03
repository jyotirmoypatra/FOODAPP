package com.example.foodorder.UserActivity

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.example.foodorder.APISERVICE.APICLIENT
import com.example.foodorder.APISERVICE.ApiInterface
import com.example.foodorder.R
import com.example.foodorder.USERMODEL.UserReqsistermodel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class userRegisterActivity : AppCompatActivity() {
    lateinit var signIn: TextView
    lateinit var signUpBtn: TextView
    lateinit var userName: TextView
    lateinit var userEmail: TextView
    lateinit var userPass: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_register)
        supportActionBar?.hide()

        signIn = findViewById(R.id.signIn)
        signUpBtn = findViewById(R.id.signUpBtn)
        userName = findViewById(R.id.userName)
        userEmail = findViewById(R.id.userEmail)
        userPass = findViewById(R.id.userPass)

        signIn.setOnClickListener {
            val intent = Intent(this, userLoginActivity::class.java)
            startActivity(intent)
            finish()
        }


        signUpBtn.setOnClickListener {
            registerUser()
        }

    }

    private fun registerUser() {


        val uname = userName.text.toString().trim()
        val uemail = userEmail.text.toString().trim()
        val upass = userPass.text.toString().trim()

        if(uname!="" && uemail!="" && upass!=""){
            val user = UserReqsistermodel()
            user.uname=uname
            user.uemail=uemail
            user.upass=upass

            val progressBar = ProgressDialog(this)
            progressBar.setCancelable(false)
            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER)
            progressBar.setMessage("Please Wait ...")
            progressBar.show()

            val retrofit = APICLIENT.getInstance().create(ApiInterface::class.java)
            retrofit.registerUser(user)
                ?.enqueue(object : Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Toast.makeText(this@userRegisterActivity,"Error Login!!", Toast.LENGTH_SHORT).show()
                        Log.e("err",""+t)
                        progressBar.dismiss();
                    }

                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        if (response.isSuccessful) {
                            progressBar.dismiss();
                            val userRes = response.body().toString()


                            val intent =
                                Intent(this@userRegisterActivity,userLoginActivity::class.java)
                            startActivity(intent)
                            finish()


                        } else {
                            Toast.makeText(this@userRegisterActivity,"Something Went Wrong!!!", Toast.LENGTH_SHORT).show()
                            progressBar.dismiss();
                        }

                    }
                })

        }else{
            Toast.makeText(this@userRegisterActivity,"Please Enter Details!", Toast.LENGTH_SHORT).show()
        }


    }
}