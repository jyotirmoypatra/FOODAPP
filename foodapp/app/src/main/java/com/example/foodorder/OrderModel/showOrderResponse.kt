package com.example.foodorder.OrderModel

import com.google.gson.annotations.SerializedName

class showOrderResponse {
    @SerializedName("orderlist" ) var orderlist : ArrayList<Orderlist> = arrayListOf()
}

class Orderlist{
    @SerializedName("orderid"     ) var orderid     : String?         = null
    @SerializedName("payment"     ) var payment     : String?         = null
    @SerializedName("transaction" ) var transaction : String?         = null
    @SerializedName("price"       ) var price       : String?         = null
    @SerializedName("food"        ) var food        : ArrayList<Food> = arrayListOf()
}
class Food{
    @SerializedName("foodname" ) var foodname : String? = null
    @SerializedName("quantity" ) var quantity : String? = null
}