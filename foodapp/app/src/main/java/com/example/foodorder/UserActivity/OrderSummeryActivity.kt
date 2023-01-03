package com.example.foodorder.UserActivity

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.util.Log
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import com.example.foodorder.APISERVICE.APICLIENT
import com.example.foodorder.APISERVICE.ApiInterface
import com.example.foodorder.Common.cms
import com.example.foodorder.OrderModel.OrderItem
import com.example.foodorder.OrderModel.OrderItemResponse
import com.example.foodorder.OrderModel.OrderRequest
import com.example.foodorder.OrderModel.OrderResponse
import com.example.foodorder.R
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderSummeryActivity : AppCompatActivity() ,PaymentResultListener{
    lateinit var cod: LinearLayout
    lateinit var razorpay: LinearLayout
    lateinit var backBtn: ImageView
    lateinit var circleCod: ImageView
    lateinit var circleRazor: ImageView
    lateinit var orderAddress: EditText
    lateinit var orderPrice: TextView
    lateinit var placeOrder: TextView
    var transectionId = ""
    var paymentType = ""
    var sum = 0
    var boolCod: Boolean = false
    var boolRazorpay: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_summery)
        backBtn = findViewById(R.id.backBtn)
        backBtn.setOnClickListener {
            super.onBackPressed()
        }

        cod = findViewById(R.id.cod)
        razorpay = findViewById(R.id.razorpay)
        circleCod = findViewById(R.id.circleCod)
        circleRazor = findViewById(R.id.circleRazor)
        orderAddress = findViewById(R.id.orderAddress)
        orderPrice = findViewById(R.id.orderPrice)
        placeOrder = findViewById(R.id.placeOrder)


        val sharedPreferences: SharedPreferences =
            getSharedPreferences("user", Context.MODE_PRIVATE)
        val address: String? = sharedPreferences.getString("address", "")
        orderAddress.text = Editable.Factory.getInstance().newEditable(address)


        for (i in 0 until cms.myList.size) {
            sum = sum + (cms.myList[i].foodPrice!!.toInt() * cms.myList[i].foodQty!!.toInt())
        }

        orderPrice.text = sum.toString()


        ///razorpay initializer
        Checkout.preload(this@OrderSummeryActivity);




        cod.setOnClickListener {
            circleCod.setImageResource(R.drawable.ic_circle_check)
            circleRazor.setImageResource(R.drawable.ic_round)
            boolCod = true
            boolRazorpay = false
        }


        razorpay.setOnClickListener {
            circleRazor.setImageResource(R.drawable.ic_circle_check)
            circleCod.setImageResource(R.drawable.ic_round)
            boolCod = false
            boolRazorpay = true

        }

        placeOrder.setOnClickListener {
            if(boolCod==false && boolRazorpay==false){
                Toast.makeText(this@OrderSummeryActivity, "Please Select Payment Option!!", Toast.LENGTH_SHORT)
                    .show()

            }else {
                PlaceOrder()
            }
        }

    }

    private fun PlaceOrder() {

        if (boolCod) {
            paymentType = "COD"
            transectionId = "null"

            OrderApiCall()

        } else {
            paymentType = "RAZORPAY"
            RazorPayCall(sum)
        }


    }

    private fun OrderApiCall() {
        val progressBar = ProgressDialog(this)
        progressBar.setCancelable(false)
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressBar.setMessage("Your Order is Processing...")
        progressBar.show()

        val sharedPreferences: SharedPreferences =
            getSharedPreferences("user", Context.MODE_PRIVATE)
        val uid: String? = sharedPreferences.getString("id", "")


            val orderRequest = OrderRequest()
            orderRequest.userid = uid!!.toInt()
            orderRequest.payment_type = paymentType
            orderRequest.transaction_id = transectionId
            orderRequest.total_price = sum.toString()


            val retrofit = APICLIENT.getInstance().create(ApiInterface::class.java)
            retrofit.orderInsert(orderRequest)
                ?.enqueue(object : Callback<OrderResponse> {
                    override fun onFailure(call: Call<OrderResponse>, t: Throwable) {
                        Toast.makeText(this@OrderSummeryActivity, "Error!!", Toast.LENGTH_SHORT)
                            .show()
                        Log.e("err", "order err" + t)
                        progressBar.dismiss();
                    }

                    override fun onResponse(
                        call: Call<OrderResponse>,
                        response: Response<OrderResponse>
                    ) {
                        if (response.isSuccessful) {

                            val orderRes = response.body()
                            var orderId = orderRes!!.orderid

                            OrderItemApi(orderId, progressBar)      //item insert in db api call


                        } else {
                            Toast.makeText(
                                this@OrderSummeryActivity,
                                "Something Went Wrong!!!",
                                Toast.LENGTH_SHORT
                            ).show()
                            progressBar.dismiss();
                        }

                    }
                })

    }

    private fun RazorPayCall(sum:Int) {
       val checkout =Checkout()
        checkout.setKeyID("rzp_live_Lsv5wpXiI3P2bd")
        try{
           val options =JSONObject()
            options.put("name","FOODBITE")
            options.put("description","You can’t stop eating, so go!")
            options.put("theme.color","#FF5722")
            options.put("currency","INR")
            options.put("amount",sum*100)

            val retryObj = JSONObject()
            retryObj.put("enabled",true)
            retryObj.put("max_count",4)
            options.put("retry",retryObj)

            checkout.open(this@OrderSummeryActivity,options)

        }catch(e: Exception){
            Toast.makeText(this@OrderSummeryActivity, "Error in Payment!!", Toast.LENGTH_SHORT)
                          .show()
        }

    }

    private fun OrderItemApi(orderId:Int?,progressbar:ProgressDialog) {
        progressbar.setMessage("Please Wait ...")
        var ErrorArr =  ArrayList<String>()
       for(i in 0 until cms.myList.size) {
           var order = OrderItem()
           order.order_id = orderId
           order.food_id = cms.myList[i].id!!.toInt()
           order.Quantity = cms.myList[i].foodQty

           var retrofit = APICLIENT.getInstance().create(ApiInterface::class.java)
           retrofit.itemOrder(order)
               ?.enqueue(object : Callback<OrderItemResponse> {
                   override fun onFailure(call: Call<OrderItemResponse>, t: Throwable) {
//                       Toast.makeText(this@OrderSummeryActivity, "Error!!", Toast.LENGTH_SHORT)
//                           .show()
                       Log.e("err", "item order err" + t)
                       ErrorArr.add("err")

                   }

                   override fun onResponse(
                       call: Call<OrderItemResponse>,
                       response: Response<OrderItemResponse>
                   ) {
                       if (response.isSuccessful) {

                           val itemOrder = response.body()
                           var status = itemOrder!!.status




                       } else {
                           ErrorArr.add("err")
                           Toast.makeText(
                               this@OrderSummeryActivity,
                               "Something Went Wrong!!!",
                               Toast.LENGTH_SHORT
                           ).show()

                       }

                   }
               })
       }

           if(ErrorArr.isEmpty()){

               val mp = MediaPlayer.create(this, R.raw.twinkling);
               mp.start()
               Handler().postDelayed({
                   mp.stop()
               }, 2200)

               progressbar.dismiss()
               val dialog = Dialog(this@OrderSummeryActivity,android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen)

               dialog.setCancelable(false)

               dialog.setContentView(R.layout.order_success)
               val window: Window? = dialog.window
               window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

               val orderPage= dialog.findViewById(R.id.orderPage) as TextView

               cms.myList.clear()
               orderPage.setOnClickListener{

                   dialog.dismiss()
                   val i = Intent(this@OrderSummeryActivity,userProfileActivity::class.java)
                   cms.boolOrderSuccess=true
                   startActivity(i)
               }

               dialog.show()

                   ///clear cart item

//               dialog.setOnDismissListener {
//                   val i = Intent(this@OrderSummeryActivity,userProfileActivity::class.java)
//                  cms.boolOrderSuccess=true
//                   startActivity(i)
//               }


           }else{
               progressbar.dismiss()
               val dialog = Dialog(this@OrderSummeryActivity)
               dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
               dialog.setCancelable(false)

               dialog.setContentView(R.layout.error)
               val window: Window? = dialog.window
               window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

               val okBtn= dialog.findViewById(R.id.okBtn) as TextView
               val text= dialog.findViewById(R.id.text) as TextView
               text.text="Something went Wrong!!!"
               text.setTextColor(Color.RED)

               okBtn.setOnClickListener{
                   dialog.dismiss()
               }

               dialog.show()
           }

    }

    override fun onPaymentSuccess(p0: String?) {
        transectionId=p0.toString()
        OrderApiCall()
    }

    override fun onPaymentError(p0: Int, p1: String?) {

        val dialog = Dialog(this@OrderSummeryActivity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)

        dialog.setContentView(R.layout.error)
        val window: Window? = dialog.window
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        val okBtn= dialog.findViewById(R.id.okBtn) as TextView
        val text= dialog.findViewById(R.id.text) as TextView
        text.text="Payment Failed!!!"
        text.setTextColor(Color.RED)

        okBtn.setOnClickListener{
            dialog.dismiss()
        }

        dialog.show()
    }
}