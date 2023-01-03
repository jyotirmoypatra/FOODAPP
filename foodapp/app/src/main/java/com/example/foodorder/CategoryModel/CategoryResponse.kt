package com.example.foodorder.CategoryModel

import com.google.gson.annotations.SerializedName


data class CategoryResponseModel (

    @SerializedName("catlist" ) var catlist : ArrayList<Catlist> = arrayListOf()

)




data class Catlist (

    @SerializedName("id"           ) var id           : String? = null,
    @SerializedName("categoryName" ) var categoryName : String? = null

)