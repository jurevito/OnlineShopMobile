package com.example.online_shop_mobile.pojo

import java.io.Serializable

data class Order(
    val id: Int = 0,
    val id_user: Int = 0,
    val id_seller: Int = 0,
    val status: Int = 0) : Serializable
