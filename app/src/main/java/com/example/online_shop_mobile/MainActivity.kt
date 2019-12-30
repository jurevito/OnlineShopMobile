package com.example.online_shop_mobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.Toast
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

        val name = intent.getStringExtra("name")
        val lastname = intent.getStringExtra("lastname")
        val email = intent.getStringExtra("email")
        val address = intent.getStringExtra("address")
        val zipcode = intent.getIntExtra("zipcode", -1)
        val phone = intent.getStringExtra("phone")

        // <---NAVIGATION--->
        if(name != null) {
            login.text = "Profile"
        } else {
            login.text = "Login"
        }

        cart.setOnClickListener {
            Log.i("profile","Cart tab")
        }

        login.setOnClickListener {
            // user is logged in
            Log.i("debugNav","name = $name")
            if(name != null) {
                val intent = Intent(this, ProfileActivity::class.java)
                intent.putExtra("name", name)
                intent.putExtra("lastname", lastname)
                intent.putExtra("email", email)
                intent.putExtra("address", address)
                intent.putExtra("zipcode", zipcode)
                intent.putExtra("phone", phone)
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

    // idk
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
