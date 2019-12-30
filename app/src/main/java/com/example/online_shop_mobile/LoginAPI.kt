package com.example.online_shop_mobile

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

object LoginAPI {

    interface RestApi {

        companion object {
            const val URL = "http://10.0.2.2:8080/netbeans/online-shop/api/"
        }

        @FormUrlEncoded
        @POST("login")
        fun login(@Field("email") email: String,
                   @Field("password") password: String): Call<Login>
        }

    val instance: RestApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(RestApi.URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(RestApi::class.java)
    }
}