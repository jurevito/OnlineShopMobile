package com.example.online_shop_mobile

import java.io.Serializable

data class Product(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val price: Float = 0F,
    val activated: Boolean = true) : Serializable