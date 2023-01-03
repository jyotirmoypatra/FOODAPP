package com.example.foodorder.UserActivity

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.view.Window
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.foodorder.ADMINMODEL.adminLoginResponseModel
import com.example.foodorder.APISERVICE.APICLIENT
import com.example.foodorder.APISERVICE.ApiInterface
import com.example.foodorder.AdminActivity.adminDashboardActivity
import com.example.foodorder.MainActivity
import com.example.foodorder.R
import com.example.foodorder.USERMODEL.UserLoginModel
import com.example.foodorder.USERMODEL.UserLoginResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class userLoginActivity : AppCompatActivity() {

    lateinit var signUp:TextView
    lateinit var edtEmail:TextView
    lateinit var edtPass:TextView
    lateinit var signInBtn:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_login)
        supportActionBar?.hide()

        signUp=findViewById(R.id.signUp)
        edtEmail=findViewById(R.id.edtEmail)
        edtPass=findViewById(R.id.edtPass)
        signInBtn=findViewById(R.id.signInBtn)


        signUp.setOnClickListener {
            val intent = Intent(this, userRegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        signInBtn.setOnClickListener {
            UserSignIn()
        }

    }

    private fun UserSignIn() {
        val email = edtEmail.text.toString().trim()
        val pass = edtPass.text.toString().trim()

        if(email!="" && pass!=""){
            val progressBar = ProgressDialog(this)
            progressBar.setCancelable(false)
            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER)
            progressBar.setMessage("Logging, Please Wait ...")
            progressBar.show()
            val userLogin = UserLoginModel()
            userLogin.uemail=email
            userLogin.upass=pass
            val retrofit = APICLIENT.getInstance().create(ApiInterface::class.java)

            retrofit.loginUser(userLogin)
                ?.enqueue(object : Callback<UserLoginResponseModel> {
                    override fun onFailure(call: Call<UserLoginResponseModel>, t: Throwable) {
                     //   Toast.makeText(this@userLoginActivity,"Error Login!!!!!"+t,Toast.LENGTH_SHORT).show()

                        ShowErrorDialog()

                        progressBar.dismiss();
                    }

                    override fun onResponse(
                        call: Call<UserLoginResponseModel>,
                        response: Response<UserLoginResponseModel>
                    ) {
                        if (response.isSuccessful) {
                            progressBar.dismiss();
                            val userRes = response.body()

                            val intent =
                                Intent(this@userLoginActivity, MainActivity::class.java)

                            val sharedPreferences: SharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE)
                            val editor:SharedPreferences.Editor =  sharedPreferences.edit()
                            editor.putString("id", userRes?.id.toString())
                            editor.putString("name",userRes?.uname)
                            editor.putString("email",userRes?.uemail)
                            editor.putString("address",userRes?.uaddress)
                            editor.putString("phone",userRes?.uphone)
                            editor.putString("pass",userRes?.upass)
                            editor.apply()
                            editor.commit()


                            startActivity(intent)
                            finish()


                        } else {
                          //  Toast.makeText(this@userLoginActivity,"Something Went Wrong!!!",Toast.LENGTH_SHORT).show()
                            ShowErrorDialog()
                            progressBar.dismiss();
                        }

                    }
                })

        }else{
            Toast.makeText(this@userLoginActivity,"Please Enter Details!",Toast.LENGTH_SHORT).show()
        }
    }

    private fun ShowErrorDialog() {
        val dialog = Dialog(this@userLoginActivity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)

        dialog.setContentView(R.layout.error)
        val window: Window? = dialog.window
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        val okBtn= dialog.findViewById(R.id.okBtn) as TextView


        okBtn.setOnClickListener{
            dialog.dismiss()
        }

        dialog.show()
    }
}