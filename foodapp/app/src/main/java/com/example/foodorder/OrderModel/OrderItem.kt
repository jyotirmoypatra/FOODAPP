package com.example.foodorder.OrderModel

import com.google.gson.annotations.SerializedName

class OrderItem {
    @field:SerializedName("order_id")
    var order_id: Int? = null

    @field:SerializedName("food_id")
    var food_id: Int? = null

    @field:SerializedName("Quantity")
    var Quantity: Int? = null

}