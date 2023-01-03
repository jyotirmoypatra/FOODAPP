package com.example.foodorder.Fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.size
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorder.APISERVICE.APICLIENT
import com.example.foodorder.APISERVICE.ApiInterface
import com.example.foodorder.Adapter.OrderShowAdapter
import com.example.foodorder.CategoryModel.CategoryResponseModel
import com.example.foodorder.OrderModel.showOrderResponse
import com.example.foodorder.OrderModel.showOrderResquest
import com.example.foodorder.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class orderFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v:View=  inflater.inflate(R.layout.fragment_order, container, false)

        val orderList: RecyclerView =v.findViewById(R.id.orderList)

        orderList.layoutManager = LinearLayoutManager(context)



        val sharedPreferences: SharedPreferences = this.requireActivity().getSharedPreferences("user", Context.MODE_PRIVATE)
        val uid : String? =sharedPreferences.getString("id","")

        var showOrder = showOrderResquest()
        showOrder.userid=uid!!.toInt()



        val retrofit = APICLIENT.getInstance().create(ApiInterface::class.java)
        retrofit.showOrder(showOrder)
            ?.enqueue(object : Callback<showOrderResponse> {

                override fun onFailure(call: Call<showOrderResponse>, t: Throwable) {
                    Toast.makeText(
                        context,
                        "Error cat fetch " + t,
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e("err", "" + t)

                }

                override fun onResponse(
                    call: Call<showOrderResponse>,
                    response: Response<showOrderResponse>
                ) {
                    if (response.isSuccessful) {


                        var list = response.body()?.orderlist
                        var adapter = OrderShowAdapter(list)
                        orderList.adapter= adapter




                    } else {
                        Toast.makeText(
                            context,
                            "Something Went Wrong!!!",
                            Toast.LENGTH_SHORT
                        ).show()


                    }

                }
            })


        return  v
    }




}