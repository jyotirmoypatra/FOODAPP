package com.example.foodorder.USERMODEL

import com.google.gson.annotations.SerializedName

class UserReqsistermodel {

    @field:SerializedName("uname")
    var uname: String? =null

    @field:SerializedName("uemail")
    var uemail:String?=null

    @field:SerializedName("upass")
    var upass:String?=null

}