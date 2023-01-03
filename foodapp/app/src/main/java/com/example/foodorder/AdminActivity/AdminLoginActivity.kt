package com.example.foodorder.AdminActivity

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.view.Window
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.foodorder.ADMINMODEL.adminLoginReqModel
import com.example.foodorder.ADMINMODEL.adminLoginResponseModel
import com.example.foodorder.APISERVICE.APICLIENT
import com.example.foodorder.APISERVICE.ApiInterface
import com.example.foodorder.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AdminLoginActivity : AppCompatActivity() {
    lateinit var adminEmail: EditText
    lateinit var adminPass: EditText
    lateinit var adminLogin: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_login)
        supportActionBar?.hide()

        adminEmail = findViewById(R.id.adminEmail)
        adminPass = findViewById(R.id.adminPass)
        adminLogin = findViewById(R.id.adminLogin)


        adminLogin.setOnClickListener {


            loginAdmin()
        }


    }

    private fun loginAdmin() {

        val adminLoginReq = adminLoginReqModel()
        val email = adminEmail.text.toString().trim()
        val pass = adminPass.text.toString().trim()
        adminLoginReq.adEmail = email
        adminLoginReq.adPass = pass
        Log.e("admin",""+email+","+pass)

        if (email != "" && pass != "") {
            val progressBar = ProgressDialog(this)
            progressBar.setCancelable(false)
            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER)
            progressBar.setMessage("Logging, Please Wait ...")
            progressBar.show()

            val retrofit = APICLIENT.getInstance().create(ApiInterface::class.java)


            retrofit.adminLogin(adminLoginReq)
                ?.enqueue(object : Callback<adminLoginResponseModel> {
                    override fun onFailure(call: Call<adminLoginResponseModel>, t: Throwable) {
                      //  Toast.makeText(this@AdminLoginActivity,"Error Admin Login!!!!!"+t,Toast.LENGTH_SHORT).show()
                        errorDialog()
                        Log.e("err",""+t)
                        progressBar.dismiss();
                    }

                    override fun onResponse(
                        call: Call<adminLoginResponseModel>,
                        response: Response<adminLoginResponseModel>
                    ) {
                        if (response.isSuccessful) {
                            progressBar.dismiss();
                            val adminRes = response.body()

                            val intent =
                                Intent(this@AdminLoginActivity, adminDashboardActivity::class.java)
                            intent.putExtra("username", adminRes?.adName);
                            intent.putExtra("email", adminRes?.adEmail);
                            intent.putExtra("adminId", adminRes?.adid);
                            startActivity(intent)
                            finish()


                        } else {
                            //Toast.makeText(this@AdminLoginActivity,"Something Went Wrong!!!",Toast.LENGTH_SHORT).show()
                            errorDialog()
                            progressBar.dismiss();
                        }

                    }
                })
        }else{
            Toast.makeText(this@AdminLoginActivity,"PLEASE ENTER LOGIN DETAILS!!!",Toast.LENGTH_SHORT).show()
        }

    }

    private fun errorDialog() {
        val dialog = Dialog(this@AdminLoginActivity)
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




