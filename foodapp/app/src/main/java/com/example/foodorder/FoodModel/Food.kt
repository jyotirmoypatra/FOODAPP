package com.example.foodorder.FoodModel

import com.google.gson.annotations.SerializedName


data class Food (

    @SerializedName("foodlist" ) var foodlist : List<Foodlist?> = arrayListOf()

)




data class Foodlist (

    @SerializedName("id"           ) var id           : String? = null,
    @SerializedName("foodName"     ) var foodName     : String? = null,
    @SerializedName("foodCategory" ) var foodCategory : String? = null,
    @SerializedName("foodPrice"    ) var foodPrice    : String? = null,
    @SerializedName("imgurl"       ) var imgurl       : String? = null

)