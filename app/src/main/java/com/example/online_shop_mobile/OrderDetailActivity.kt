package com.example.online_shop_mobile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import com.example.online_shop_mobile.api.ProductAPI
import com.example.online_shop_mobile.api.UserAPI
import com.example.online_shop_mobile.pojo.Order
import com.example.online_shop_mobile.pojo.Product
import com.example.online_shop_mobile.pojo.User
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_order_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class OrderDetailActivity : AppCompatActivity(), Callback<User> {

    private var client: User? = null
    private var seller: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)

        val order = intent.getSerializableExtra("order") as? Order

        if (order != null) {
            UserAPI.instance.getUserData(order.id_user).enqueue(this)
            UserAPI.instance.getUserData(order.id_seller).enqueue(this)
        }
    }

    override fun onResponse(call: Call<User>, response: Response<User>) {
        val user = response.body()

        if (response.isSuccessful) {
            // user is a seller
            if(user!!.type == 1) {
                seller = user
                sellerName.text = seller!!.name+ " " + seller!!.lastName
                sellerEmail.text = seller!!.email
            }

            // user is a client
            else if(user!!.type == 2) {
                client = user
                customerName.text = client!!.name+ " " + client!!.lastName
                customerAddress.text = client!!.address
                customerPhone.text = client!!.phone
                customerEmail.text = client!!.email
            }

        } else {
            val errorMessage = try {
                "An error occurred: ${response.errorBody()!!.string()}"
            } catch (e: IOException) {
                "An error occurred: error while decoding the error message."
            }

            Log.e(TAG, errorMessage)
        }
    }

    override fun onFailure(call: Call<User>, t: Throwable) {
        Log.w(TAG, "Error: ${t.message}", t)
    }

    companion object {
        private val TAG = DetailActivity::class.java.canonicalName
    }
}
