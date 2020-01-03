package com.example.online_shop_mobile.api

import com.example.online_shop_mobile.pojo.Product
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

object ProductAPI {

    interface RestApi {

        companion object {
            const val URL = "http://10.0.2.2:8080/netbeans/online-shop/api/"
        }

        @GET("products")
        fun getAll(): Call<List<Product>>

        @GET("products/{id}")
        fun get(@Path("id") id: Int): Call<Product>
    }

    val instance: RestApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(RestApi.URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(RestApi::class.java)
    }
}