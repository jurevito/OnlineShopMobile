package com.example.online_shop_mobile

import android.util.Log
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
        @PUT("users/{idUser}")
        fun updateUserData(@Path("idUser") idUser: Int,
                   @Field("name") name: String,
                   @Field("lastName") lastName: String,
                   @Field("email") email: String,
                   @Field("password") password: String,
                   @Field("type") type: Int,
                   @Field("address") address: String,
                   @Field("zipcode_id") zipcode_id: Int,
                   @Field("phone") phone: String,
                   @Field("activated") activated: Int): Call<User>
    }

    val instance: RestApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(RestApi.URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(RestApi::class.java)
    }
}
