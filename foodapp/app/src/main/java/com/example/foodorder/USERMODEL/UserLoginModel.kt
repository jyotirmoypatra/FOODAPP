package com.example.foodorder.USERMODEL

import com.google.gson.annotations.SerializedName

class UserLoginModel {

    @field:SerializedName("uemail")
    var uemail : String? =null

    @field:SerializedName("upass")
    var upass : String? =null

}