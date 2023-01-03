package com.example.foodorder.Fragment

import android.app.Dialog
import android.app.ProgressDialog
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.util.Log
import android.view.*
import android.view.View.OnTouchListener
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.foodorder.APISERVICE.APICLIENT
import com.example.foodorder.APISERVICE.ApiInterface
import com.example.foodorder.MainActivity
import com.example.foodorder.R
import com.example.foodorder.USERMODEL.UserLoginModel
import com.example.foodorder.USERMODEL.UserLoginResponseModel
import com.example.foodorder.USERMODEL.UserUpdateModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class accountFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v:View= inflater.inflate(R.layout.fragment_account, container, false)

        val name:EditText=v.findViewById(R.id.name)
        val phone:EditText=v.findViewById(R.id.phone)
        val email:EditText=v.findViewById(R.id.email)
        val address:EditText=v.findViewById(R.id.address)
        val password:EditText=v.findViewById(R.id.password)

        val editDtl:TextView=v.findViewById(R.id.editDtl)
        val saveDtl:TextView=v.findViewById(R.id.saveDtl)
        val viewPass:ImageView=v.findViewById(R.id.viewPass)


        val sharedPreferences: SharedPreferences = this.requireActivity().getSharedPreferences("user", Context.MODE_PRIVATE)
        val uname : String? =sharedPreferences.getString("name","")
        val uid : String? =sharedPreferences.getString("id","")
        val uemail : String? =sharedPreferences.getString("email","")
        val uphone : String? =sharedPreferences.getString("phone","")
        val uaddress : String? =sharedPreferences.getString("address","")
        val upass : String? =sharedPreferences.getString("pass","")
        Log.e("user details",""+uid+uname+uemail+uphone+uaddress)

        name.text= Editable.Factory.getInstance().newEditable(uname)
        phone.text= Editable.Factory.getInstance().newEditable(uphone)
        email.text= Editable.Factory.getInstance().newEditable(uemail)
        address.text= Editable.Factory.getInstance().newEditable(uaddress)
        password.text= Editable.Factory.getInstance().newEditable("****")

        var boolShowPass :Boolean=false

        viewPass.setOnClickListener {
            if(boolShowPass){
                boolShowPass=false
                password.text= Editable.Factory.getInstance().newEditable("****")
                viewPass.setImageResource(R.drawable.ic_eye)
            }else{
                boolShowPass=true
                password.text= Editable.Factory.getInstance().newEditable(upass)
               viewPass.setImageResource(R.drawable.ic_eye_active)
            }
        }



        editDtl.setOnClickListener {

            editDtl.visibility=View.GONE
            saveDtl.visibility=View.VISIBLE

            name.setEnabled(true);
            name.setInputType(InputType.TYPE_CLASS_TEXT);
            name.setFocusable(true);
            name.setBackgroundResource(R.drawable.edittext)


            phone.setEnabled(true);
            phone.setInputType(InputType.TYPE_CLASS_TEXT);
            phone.setFocusable(true);
            phone.setBackgroundResource(R.drawable.edittext)




            address.setEnabled(true);
            address.setInputType(InputType.TYPE_CLASS_TEXT);
            address.setFocusable(true);
            address.setBackgroundResource(R.drawable.edittext)


            password.setEnabled(true);
            password.setInputType(InputType.TYPE_CLASS_TEXT);
            password.setFocusable(true);
            password.setBackgroundResource(R.drawable.edittext)

            password.text= Editable.Factory.getInstance().newEditable(upass)
        }

        saveDtl.setOnClickListener {
            var updateName = name.text.toString()
            var updatePhone = phone.text.toString()
            var updateAddress = address.text.toString()
            var updatePass = password.text.toString()

            UpdateApiUser(uid!!?.toInt(),updateName,updatePhone,updateAddress,updatePass,name,phone,address,password,saveDtl,editDtl)

        }





        return v
    }

    private fun UpdateApiUser(id:Int,name:String,phone:String,address:String,pass:String,ename:EditText,eph:EditText,eAdd:EditText,epas:EditText,savBtn:TextView,edtBtn:TextView) {


        val progressBar = ProgressDialog(context)
        progressBar.setCancelable(false)
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressBar.setMessage("Updating User, Please Wait ...")
        progressBar.show()

        val userUpdate = UserUpdateModel()
        userUpdate.uname=name
        userUpdate.uphone=phone
        userUpdate.uaddress=address
        userUpdate.upass=pass
        userUpdate.id=id

        val retrofit = APICLIENT.getInstance().create(ApiInterface::class.java)

        retrofit.updateUser(userUpdate)
            ?.enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    //   Toast.makeText(this@userLoginActivity,"Error Login!!!!!"+t,Toast.LENGTH_SHORT).show()

                    ShowErrorDialog()

                    progressBar.dismiss();
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        progressBar.dismiss();
                        ShowSuccessDialog()

                        savBtn.visibility=View.GONE
                        edtBtn.visibility=View.VISIBLE

                        ename.setEnabled(false);
                        ename.setInputType(0);
                        ename.setFocusable(false);
                        ename.setBackgroundResource(0)

                        eph.setEnabled(false);
                        eph.setInputType(0);
                        eph.setFocusable(false);
                        eph.setBackgroundResource(0)

                        eAdd.setEnabled(false);
                        eAdd.setInputType(0);
                        eAdd.setFocusable(false);
                        eAdd.setBackgroundResource(0)

                        epas.setEnabled(false);
                        epas.setInputType(0);
                        epas.setFocusable(false);
                        epas.setBackgroundResource(0)

                        val sharedPreferences: SharedPreferences = activity!!.getSharedPreferences("user", Context.MODE_PRIVATE)
                        val editor:SharedPreferences.Editor =  sharedPreferences.edit()
                        editor.putString("name",name)
                        editor.putString("address",address)
                        editor.putString("phone",phone)
                        editor.putString("pass",pass)
                        editor.apply()
                        editor.commit()


                    } else {
                        //  Toast.makeText(this@userLoginActivity,"Something Went Wrong!!!",Toast.LENGTH_SHORT).show()
                        ShowErrorDialog()
                        progressBar.dismiss();
                    }

                }
            })




    }

    private fun ShowSuccessDialog() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.error)
        val window: Window? = dialog.window
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val okBtn= dialog.findViewById(R.id.okBtn) as TextView
        val text= dialog.findViewById(R.id.text) as TextView
            text.text="Successfully Updated User"
        okBtn.setOnClickListener{
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun ShowErrorDialog() {
        val dialog = Dialog(requireContext())
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