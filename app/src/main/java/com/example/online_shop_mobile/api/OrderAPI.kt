package com.example.online_shop_mobile.api

import com.example.online_shop_mobile.pojo.Order
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

object OrderAPI {

    interface RestApi {

        companion object {
            const val URL = "http://10.0.2.2:8080/netbeans/online-shop/api/orders/"
        }

        @GET("{id}")
        fun getOrders(@Path("id") id: Int): Call<List<Order>>
    }

    val instance: RestApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(RestApi.URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(RestApi::class.java)
    }
}