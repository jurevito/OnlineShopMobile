package com.example.online_shop_mobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.Toast
import com.example.online_shop_mobile.api.ProductAPI
import com.example.online_shop_mobile.pojo.Product
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException



class MainActivity : AppCompatActivity(), Callback<List<Product>> {

    private var adapter: ProductAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val id = (application as MyApplication).id

        // <---NAVIGATION--->
        if(id != null) {
            login.text = "Profile"
        } else {
            login.text = "Login"
        }

        cart.setOnClickListener {
            // user is logged in
            if(id != null) {
                val intent = Intent(this, OrdersActivity::class.java)
                startActivity(intent)
            }

            // cant access
            else {
                Toast.makeText(this, "You have to login to view orders.", Toast.LENGTH_SHORT).show()
            }
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

        adapter = ProductAdapter(this)
        items.adapter = adapter
        items.onItemClickListener = AdapterView.OnItemClickListener { _, _, i, _ ->
            val product = adapter?.getItem(i)
            if (product != null) {
                val intent = Intent(this, DetailActivity::class.java)
                intent.putExtra("idProduct", product.id)
                startActivity(intent)
            }
        }

        container.setOnRefreshListener { ProductAPI.instance.getAll().enqueue(this) }

        ProductAPI.instance.getAll().enqueue(this)
    }


    override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
        val hits = response.body()

        if (response.isSuccessful) {
            Log.i(TAG, "Hits: " + hits!!.size)
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
        container.isRefreshing = false
    }

    override fun onFailure(call: Call<List<Product>>, t: Throwable) {
        Log.w(TAG, "Error: ${t.message}", t)
        container.isRefreshing = false
    }

    companion object {
        private val TAG = MainActivity::class.java.canonicalName
    }


}
