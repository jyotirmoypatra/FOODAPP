package com.example.foodorder.APISERVICE

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class APICLIENT {

    companion object {
        fun getInstance(): Retrofit {
            var mHttpLoggingInterceptor = HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)

            var mOkHttpClient = OkHttpClient
                .Builder()
                .addInterceptor(mHttpLoggingInterceptor)
                .build()


            var retrofit: Retrofit = retrofit2.Retrofit.Builder()
                .baseUrl("https://chichi-feather.000webhostapp.com/")
                .addConverterFactory(GsonConverterFactory.create())

                .client(mOkHttpClient)
                .build()
            return retrofit
        }
    }

}


