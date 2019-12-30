package com.example.online_shop_mobile

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

object UserAPI {

    interface RestApi {

        companion object {
            const val URL = "http://10.0.2.2:8080/netbeans/online-shop/api/"
        }

        @GET("users/{id}")
        fun getUserData(@Path("id") id: Int): Call<User>

        @FormUrlEncoded
        @PUT("users/{id}")
        fun updateUserData(@Path("id") id: Int,
                   @Field("title") title: String,
                   @Field("price") price: Float,
                   @Field("description") description: String,
                   @Field("activated") activated: Boolean): Call<Void>
    }

    val instance: RestApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(RestApi.URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(RestApi::class.java)
    }
}
