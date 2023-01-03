package com.example.foodorder.USERMODEL

import com.google.gson.annotations.SerializedName

class UserLoginResponseModel {

    @field:SerializedName("id")
    var id : Int? =null

    @field:SerializedName("uname")
    var uname : String? =null


    @field:SerializedName("uemail")
    var uemail : String? =null

    @field:SerializedName("upass")
    var upass : String? =null

    @field:SerializedName("uphone")
    var uphone : String? =null

    @field:SerializedName("uaddress")
    var uaddress : String? =null

}