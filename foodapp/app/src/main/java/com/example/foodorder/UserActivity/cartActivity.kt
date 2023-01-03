package com.example.foodorder.UserActivity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dd.CircularProgressButton
import com.example.foodorder.Adapter.CartAdapter
import com.example.foodorder.Common.cms
import com.example.foodorder.FoodModel.CartModel
import com.example.foodorder.Interface.AddToCart
import com.example.foodorder.Interface.DeleteItem
import com.example.foodorder.Interface.FoodQuantityChange
import com.example.foodorder.MainActivity
import com.example.foodorder.R
import org.json.JSONArray


class cartActivity : AppCompatActivity() {
    lateinit var cartCounter: TextView
    lateinit var home: TextView
    lateinit var backBtn: ImageView
    lateinit var txtTotalPrice: TextView
    lateinit var txtPriceDtl: TextView
    lateinit var txtPayablePrice: TextView
    lateinit var cartRecycler: RecyclerView
    lateinit var cartAdapter: CartAdapter
    lateinit var foodQuantityChange: FoodQuantityChange
    lateinit var deleteItem: DeleteItem
    lateinit var checkout: TextView

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        supportActionBar?.hide()

        home = findViewById(R.id.home)
        backBtn = findViewById(R.id.backBtn)
        txtPriceDtl = findViewById(R.id.txtPriceDtl)
        home.setOnClickListener {
            val i = Intent(this@cartActivity, MainActivity::class.java)
            startActivity(i)
            finish()
        }
        backBtn.setOnClickListener {
            finish();
        }
        cartCounter = findViewById(R.id.cartCounter)



        cartCounter.text = cms.myList.size.toString()

        Log.e("receive", "list>>>>>>>>>>>>>" + cms.myList)
        Log.e("receive size", "list>>>>>>>>>>>>>" + cms.myList.size)


        val cartPriceArrayList = ArrayList<String>()


        var totalPrice = 0
        for (i in cms.myList) {

            cartPriceArrayList.add(i.foodPrice.toString())
            totalPrice += i.foodPrice!!.toInt()

        }

        txtTotalPrice = findViewById(R.id.txtTotalPrice)
        txtPayablePrice = findViewById(R.id.txtPayablePrice)

        txtTotalPrice.text = totalPrice.toString()
        txtPayablePrice.text = totalPrice.toString()

        txtPriceDtl.text = "Price ( " + cms.myList.size + " items )"


        //implement food quantity
        foodQuantityChange = object : FoodQuantityChange {
            override fun onChangePrice(position: Int, price: Int) {
                Log.e("Cart price list", "" + cartPriceArrayList)
                Log.e("position", "" + position)
                cartPriceArrayList.set(position, price.toString())
                Log.e("new price list-", "" + cartPriceArrayList)
                var sum = 0
                for (i in cartPriceArrayList) {
                    sum += i.toInt()

                }
                txtTotalPrice.text = sum.toString()
                txtPayablePrice.text = sum.toString()




            }
        }


        //implement delete item
        deleteItem = object : DeleteItem {
            override fun delete(position: Int?) {

                cms.myList.removeAt(position!!?.toInt())

                cartAdapter.updateList(cms.myList)
                cartCounter.text = cms.myList.size.toString()
                //cms.listSize= cms.myList.size
                var s = 0
                for (i in cms.myList) {
                    s += i.foodPrice!!.toInt()
                }
                txtTotalPrice.text = s.toString()
                txtPayablePrice.text = s.toString()
                txtPriceDtl.text = "Price ( " + cms.myList.size + " items )"


            }
        }

        cartRecycler = findViewById(R.id.cartRecycler)
        cartRecycler.layoutManager = LinearLayoutManager(this)
        cartAdapter = CartAdapter(cms.myList, foodQuantityChange, deleteItem)
        cartRecycler.adapter = cartAdapter



        checkout=findViewById(R.id.checkout)
        checkout.setOnClickListener {


            for(i in 0 until cms.myList.size){
                Log.e("list",""+cms.myList.get(i).foodQty)
                Log.e("list",""+cms.myList.get(i).foodName)
            }

         val i = Intent(this@cartActivity,OrderSummeryActivity::class.java)
            startActivity(i)

        }



    }


}