package com.example.online_shop_mobile.pojo

import java.io.Serializable

data class ProductOrder(
    val id_order: Int = 0,
    val id_product: Int = 0,
    val amount: Int = 0) : Serializable