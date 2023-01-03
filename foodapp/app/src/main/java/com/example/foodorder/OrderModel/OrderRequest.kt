package com.example.foodorder.OrderModel

import com.google.gson.annotations.SerializedName

class OrderRequest {
    @field:SerializedName("userid")
    var userid : Int? =null

    @field:SerializedName("payment_type")
    var payment_type : String? =null

    @field:SerializedName("transaction_id")
    var transaction_id : String? =null


    @field:SerializedName("total_price")
    var total_price : String? =null
}