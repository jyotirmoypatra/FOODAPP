package com.example.foodorder.APISERVICE

import com.example.foodorder.ADMINMODEL.adminLoginReqModel
import com.example.foodorder.ADMINMODEL.adminLoginResponseModel
import com.example.foodorder.ADMINMODEL.categoryModel
import com.example.foodorder.CategoryModel.CategoryResponseModel
import com.example.foodorder.FoodModel.Food
import com.example.foodorder.OrderModel.*
import com.example.foodorder.USERMODEL.UserLoginModel
import com.example.foodorder.USERMODEL.UserLoginResponseModel
import com.example.foodorder.USERMODEL.UserReqsistermodel
import com.example.foodorder.USERMODEL.UserUpdateModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import java.util.*


interface ApiInterface {
    @Headers("Content-Type:application/json")
    @POST("loginadmin")
    fun adminLogin(@Body loginRequest: adminLoginReqModel?): Call<adminLoginResponseModel>


    @Headers("Content-Type:application/json")
    @POST("addfoodcategory")
    fun addCategory(@Body addcategoryreq: categoryModel?): Call<ResponseBody>


    @Headers("Content-Type:application/json")
    @POST("registeruser")
    fun registerUser(@Body userReq: UserReqsistermodel?): Call<ResponseBody>


    @Headers("Content-Type:application/json")
    @POST("loginuser")
    fun loginUser(@Body loginReq: UserLoginModel?): Call<UserLoginResponseModel>

    @Headers("Content-Type:application/json")
    @GET("fetchcategorylist")
    fun getCategoryList(): Call<CategoryResponseModel>



    ///multipart image///////////////////////////////////
    @Multipart
    @POST("addfood")
    fun addFood(
        @Part("foodName") foodName: RequestBody,
        @Part("foodCategory") foodCategory: RequestBody,
        @Part("foodPrice") foodPrice: RequestBody,
        @Part image: MultipartBody.Part?
    ): Call<ResponseBody>



    @Headers("Content-Type:application/json")
    @GET("fetchfood")
    fun getFoodList(): Call<Food>



    @Headers("Content-Type:application/json")
    @POST("updateuser")
    fun updateUser(@Body updateReq: UserUpdateModel?): Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @POST("orderInsert")
    fun orderInsert(@Body orderRequest: OrderRequest?): Call<OrderResponse>



    @Headers("Content-Type:application/json")
    @POST("itemorder")
    fun itemOrder(@Body orderitem: OrderItem?): Call<OrderItemResponse>

    @Headers("Content-Type:application/json")
    @POST("showOrder")
    fun showOrder(@Body showOrderResquest: showOrderResquest?): Call<showOrderResponse>

}