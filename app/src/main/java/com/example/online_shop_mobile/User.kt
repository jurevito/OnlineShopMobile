package com.example.online_shop_mobile

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


/*
{
    "id": "3",
    "name": "Jure",
    "lastName": "Vito",
    "email": "vito@ep.si",
    "password": "$2y$10$.6ZJL/PP3GyIYPDMQdPT9O4DRYCYq.4n0Gfck1QCxw2gKZ/qwaiu6",
    "type": "2",
    "address": "Večna pot 113",
    "zipcode_id": "5",
    "phone": "040123456",
    "activated": "1"
}
 */