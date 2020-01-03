package com.example.online_shop_mobile.pojo

import java.io.Serializable

data class User(
    val id: Int = 0,
    val name: String = "",
    val lastName: String = "",
    val email: String = "",
    val password: String = "",
    val type: Int = 0,
    val address: String = "",
    val zipcode_id: Int = 0,
    val phone: String = "",
    val activated: Int = 0) : Serializable
