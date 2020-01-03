package com.example.online_shop_mobile.pojo

import java.io.Serializable

data class Login(
    val loggedin: Boolean = true,
    val name: String = "",
    val lastName: String = "",
    val email: String = "",
    val id: Int = 0,
    val address: String = "",
    val zipcode: Int = 0,
    val phone: String = "",
    val activated: Int = 0) : Serializable


/*
 "loggedin": true,
    "id": 3,
    "name": "Jure",
    "lastName": "Vito",
    "email": "vito@ep.si",
    "type": 2,
    "address": "Ve?na pot 113",
    "zipcode_id": 5,
    "phone": "040123456",
    "activated": 1
 */