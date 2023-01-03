package com.example.foodorder

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.TypedValue
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorder.APISERVICE.APICLIENT
import com.example.foodorder.APISERVICE.ApiInterface
import com.example.foodorder.Adapter.FoodAdapter
import com.example.foodorder.CategoryModel.CategoryResponseModel
import com.example.foodorder.FoodModel.CartModel
import com.example.foodorder.FoodModel.Food
import com.example.foodorder.FoodModel.Foodlist
import com.example.foodorder.Interface.AddToCart
import com.example.foodorder.UserActivity.cartActivity
import com.example.foodorder.Common.cms
import com.example.foodorder.UserActivity.userProfileActivity
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    lateinit var recyclerview: RecyclerView
    lateinit var searchFood: EditText
    lateinit var foodRes: List<Foodlist>
    lateinit var adapter: FoodAdapter
    lateinit var filterIcon: ImageView
    lateinit var listCat: ArrayList<String>
    lateinit var filterCategoryItem: ArrayList<String>
    lateinit var loading: ProgressBar
    lateinit var addToCart: AddToCart
    lateinit var cartCounter: TextView
    lateinit var goCart: LinearLayout
    lateinit var account: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        recyclerview = findViewById(R.id.recyclerview)
        searchFood = findViewById(R.id.searchFood)
        filterIcon = findViewById(R.id.filterIcon)
        loading = findViewById(R.id.loading)
        cartCounter = findViewById(R.id.cartCounter)
        goCart = findViewById(R.id.goCart)
        account = findViewById(R.id.account)


        account.setOnClickListener {
            val i= Intent(this,userProfileActivity::class.java)
            startActivity(i)
        }


        foodRes = ArrayList()
        listCat = ArrayList()
        filterCategoryItem = ArrayList()

        cartCounter.text = cms.myList.size.toString()

        //add to cart implementation
        addToCart = object : AddToCart {
            override fun onClick(id: String?, foodName: String?, foodPrice: String?) {
                var cartItem = CartModel()
                cartItem.id = id
                cartItem.foodName = foodName
                cartItem.foodPrice = foodPrice
                var count = 0

                for (i in cms.myList) {
                    if (i.foodName.equals(foodName)) {
                        count++
                        break
                    }

                }
                if (count == 0) {
                    cms.myList.add(cartItem)
                } else {
                    Toast.makeText(this@MainActivity, "Item Already In Cart", Toast.LENGTH_SHORT)
                        .show()
                }



                cartCounter.text = cms.myList.size.toString()
                Log.e("obj cart list", "" + cms.myList)
            }
        }


        adapter = FoodAdapter(null, addToCart)

        recyclerview.layoutManager = LinearLayoutManager(this)

        ApiCategoryList()   ///api call for category list for filter
        ApiFetchAllFoodList()

        searchFood.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                filter(s.toString())
                Log.e("search item", "" + s)

            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun afterTextChanged(s: Editable) {


            }
        })



        filterIcon.setOnClickListener {

            showFilterDialog()
        }




        goCart.setOnClickListener {
            val i = Intent(this@MainActivity, cartActivity::class.java)
            startActivity(i)

        }


    }

    private fun ApiCategoryList() {

        val retrofit = APICLIENT.getInstance().create(ApiInterface::class.java)
        retrofit.getCategoryList()
            ?.enqueue(object : Callback<CategoryResponseModel> {
                override fun onFailure(call: Call<CategoryResponseModel>, t: Throwable) {
                    Toast.makeText(
                        this@MainActivity,
                        "Error cat fetch " + t,
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e("err", "" + t)

                }

                override fun onResponse(
                    call: Call<CategoryResponseModel>,
                    response: Response<CategoryResponseModel>
                ) {
                    if (response.isSuccessful) {


                        var catlist = response.body()?.catlist

                        //  Log.e("list", "" + catlist?.size)
                        listCat = java.util.ArrayList<String>()
                        for (i in 0 until catlist!!?.size) {

                            // Log.e("cat name",""+catlist.get(i).categoryName)
                            listCat.add(catlist.get(i).categoryName.toString())

                        }

                        //    Log.e("list-----", "" + list)


                    } else {
                        Toast.makeText(
                            this@MainActivity,
                            "Something Went Wrong!!!",
                            Toast.LENGTH_SHORT
                        ).show()

                    }

                }
            })


    }

    private fun showFilterDialog() {
        val dialog = Dialog(this@MainActivity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)

        dialog.setContentView(R.layout.filterdialog)
        val window: Window? = dialog.window
        window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val wlp = window!!.attributes
        wlp.gravity = Gravity.TOP or Gravity.RIGHT

        wlp.width = LinearLayout.LayoutParams.WRAP_CONTENT
        wlp.flags = wlp.flags and WindowManager.LayoutParams.FLAG_DIM_BEHIND.inv()
        wlp.y = 260   //if you want give margin from top
        window!!.attributes = wlp

        var filterRow = dialog.findViewById(R.id.filterRow) as LinearLayout
        Log.e("list", "inside dialog filter list " + listCat)
        for (i in listCat) {
            val llRow = LinearLayout(this)
            llRow.setLayoutParams(
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            )
            llRow.setOrientation(LinearLayout.HORIZONTAL);

            val checkBox = CheckBox(this)
            checkBox.setLayoutParams(
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            )
            llRow.addView(checkBox)

            val catTxt = TextView(this)
            catTxt.setLayoutParams(
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            )
            catTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18F);
            catTxt.setTypeface(null, Typeface.BOLD)
            catTxt.setTextColor(Color.BLACK)
            catTxt.text = i.toString()
            llRow.addView(catTxt)


            checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    filterCategoryItem.add(i)
                    Log.e("checked click", "" + i)
                }
            }


            filterRow.addView(llRow)
        }


        //filter food
        val filterData = dialog.findViewById(R.id.filterData) as TextView
        filterData.setOnClickListener {
            if (filterCategoryItem.isEmpty()) {
                adapter.updateList(foodRes)
            } else {
                val filterArrayList: MutableList<Foodlist> = ArrayList()
                for (i in filterCategoryItem) {
                    for (j in foodRes) {
                        if (j.foodCategory.toString().lowercase()
                                .equals(i.toString().lowercase())
                        ) {
                            filterArrayList.add(j)
                            Log.e("filter food", "food name" + j.foodName)
                        }
                    }
                }

                adapter.updateList(filterArrayList)

            }
            dialog.dismiss()

        }


        val clearBtn = dialog.findViewById(R.id.clearBtn) as TextView
        clearBtn.setOnClickListener {
            adapter.updateList(foodRes)
            dialog.dismiss()
        }


        val closeFilter = dialog.findViewById(R.id.closeFilter) as TextView
        closeFilter.setOnClickListener {

            dialog.dismiss()
        }
        dialog.show()


        dialog.setOnDismissListener(DialogInterface.OnDismissListener {
            filterCategoryItem.clear()
        })

    }


    fun filter(text: String?) {
        Log.e("search item", "inside function =>>>>>>" + text)
        val temp: MutableList<Foodlist> = ArrayList()
        for (d in foodRes) {
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if (d.foodName.toString().lowercase().contains(text.toString())) {
                temp.add(d)
                // Log.e("food name",""+d.foodName)
            }
        }
        //update recyclerview
        adapter.updateList(temp)

    }

    private fun ApiFetchAllFoodList() {
        loading.visibility = View.VISIBLE

        val retrofit = APICLIENT.getInstance().create(ApiInterface::class.java)


        retrofit.getFoodList()
            ?.enqueue(object : Callback<Food> {
                override fun onFailure(call: Call<Food>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Error Fetching Food!", Toast.LENGTH_SHORT)
                        .show()
                    loading.visibility = View.GONE

                }

                override fun onResponse(
                    call: Call<Food>,
                    response: Response<Food>
                ) {
                    if (response.isSuccessful) {

                        foodRes = response.body()?.foodlist as List<Foodlist>
                        //   Log.e("food list",""+foodRes)

                        adapter = FoodAdapter(foodRes, addToCart)
                        recyclerview.adapter = adapter
                        loading.visibility = View.GONE
                    } else {
                        Toast.makeText(
                            this@MainActivity,
                            "Something Went Wrong!!!",
                            Toast.LENGTH_SHORT
                        ).show()
                        loading.visibility = View.GONE

                    }

                }
            })
    }

    override fun onResume() {
        cartCounter.text = cms.myList.size.toString()
        super.onResume()
        Log.e("resume main", "")
    }


}

