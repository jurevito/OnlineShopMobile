package com.example.online_shop_mobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.Toast
import com.example.online_shop_mobile.api.OrderAPI
import com.example.online_shop_mobile.pojo.Order
import kotlinx.android.synthetic.main.activity_orders.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException



class OrdersActivity : AppCompatActivity(), Callback<List<Order>> {

    private var adapter: OrderAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders)
        val id = (application as MyApplication).id

        // <---NAVIGATION--->
        if(id != null) {
            login.text = "Profile"
        } else {
            login.text = "Login"
        }

        cart.setOnClickListener {
            val intent = Intent(this, OrdersActivity::class.java)
            startActivity(intent)
        }

        store.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        login.setOnClickListener {
            // user is logged in
            if(id != null) {
                val intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
            }

            // login
            else {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
        // <---END--->

        // display all orders of a user
        adapter = OrderAdapter(this)
        ordersItems.adapter = adapter
        ordersItems.onItemClickListener = AdapterView.OnItemClickListener { _, _, i, _ ->
            val order = adapter?.getItem(i)
            if (order != null) {
                val intent = Intent(this, DetailActivity::class.java)
                intent.putExtra("idProduct", order.id)
                startActivity(intent)
            }
        }

        // get all orders of a user
        if(id != null) {
            orderContainer.setOnRefreshListener { OrderAPI.instance.getOrders(id).enqueue(this) }
            OrderAPI.instance.getOrders(id).enqueue(this)
        } else {
            Toast.makeText(this, "You have to login to view orders.", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>) {
        val hits = response.body()

        if (response.isSuccessful) {
            Log.i(TAG, "Result size: " + hits!!.size)
            adapter?.clear()
            adapter?.addAll(hits)
        } else {
            val errorMessage = try {
                "An error occurred: ${response.errorBody()!!.string()}"
            } catch (e: IOException) {
                "An error occurred: error while decoding the error message."
            }

            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            Log.e(TAG, errorMessage)
        }
        orderContainer.isRefreshing = false
    }

    override fun onFailure(call: Call<List<Order>>, t: Throwable) {
        Log.w(TAG, "Error: ${t.message}", t)
        orderContainer.isRefreshing = false
    }

    companion object {
        private val TAG = MainActivity::class.java.canonicalName
    }


}
