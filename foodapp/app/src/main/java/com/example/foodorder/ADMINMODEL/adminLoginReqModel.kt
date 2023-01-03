package com.example.foodorder.ADMINMODEL

import com.google.gson.annotations.SerializedName

class adminLoginReqModel {
    @field:SerializedName("ad_email")
     var adEmail: String? =null

    @field:SerializedName("ad_pass")
   var adPass:String?=null
}