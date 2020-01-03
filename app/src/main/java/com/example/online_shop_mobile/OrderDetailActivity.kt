package com.example.online_shop_mobile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import com.example.online_shop_mobile.api.OrderAPI
import com.example.online_shop_mobile.api.UserAPI
import com.example.online_shop_mobile.pojo.Order
import com.example.online_shop_mobile.pojo.ProductOrder
import com.example.online_shop_mobile.pojo.User
import kotlinx.android.synthetic.main.activity_order_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class OrderDetailActivity : AppCompatActivity() {

    private var total = 0
    private var adapter: OrderProductAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)

        val order = intent.getSerializableExtra("order") as? Order

        UserAPI.instance.getUserData(order!!.id_user).enqueue(object : Callback<User> {

            override fun onFailure(call: Call<User>?, t: Throwable?) {
                Log.w(TAG, "Error: ${t!!.message}", t)
            }

            override fun onResponse(call: Call<User>?, response: Response<User>?) {

                val user = response!!.body()

                if (response.isSuccessful) {
                    // user is a client
                    customerName.text = user!!.name+ " " + user!!.lastName
                    customerAddress.text = user!!.address
                    customerPhone.text = user!!.phone
                    customerEmail.text = user!!.email
                } else {
                    val errorMessage = try {
                        "An error occurred: ${response.errorBody()!!.string()}"
                    } catch (e: IOException) {
                        "An error occurred: error while decoding the error message."
                    }
                    Log.e(TAG, errorMessage)
                }
            }
        })



        UserAPI.instance.getUserData(order!!.id_seller).enqueue(object : Callback<User> {

            override fun onFailure(call: Call<User>?, t: Throwable?) {
                Log.w(TAG, "Error: ${t!!.message}", t)
            }

            override fun onResponse(call: Call<User>?, response: Response<User>?) {
                val user = response!!.body()
                if (response.isSuccessful) {
                    // user is a seller
                    sellerName.text = user!!.name+ " " + user!!.lastName
                    sellerEmail.text = user!!.email
                } else {
                    val errorMessage = try {
                        "An error occurred: ${response.errorBody()!!.string()}"
                    } catch (e: IOException) {
                        "An error occurred: error while decoding the error message."
                    }
                    Log.e(TAG, errorMessage)
                }
            }
        })


        // getting all products of an order
        OrderAPI.instance.getOrderProducts(order!!.id).enqueue(object : Callback<List<ProductOrder>> {

            override fun onFailure(call: Call<List<ProductOrder>>, t: Throwable?) {
                Log.w(TAG, "Error: ${t!!.message}", t)
            }

            override fun onResponse(call: Call<List<ProductOrder>>, response: Response<List<ProductOrder>>) {

                val orderProducts = response.body()

                if (response.isSuccessful) {
                    Log.i("debug", "Result da deva: " + orderProducts.toString())
                    adapter?.clear()
                    adapter?.addAll(orderProducts!!.toMutableList())

                } else {
                    val errorMessage = try {
                        "An error occurred: ${response.errorBody()!!.string()}"
                    } catch (e: IOException) {
                        "An error occurred: error while decoding the error message."
                    }
                    Log.e(TAG, errorMessage)
                }
            }
        })

        adapter = OrderProductAdapter(this)
        productList.adapter = adapter
    }

    companion object {
        private val TAG = DetailActivity::class.java.canonicalName
    }
}
