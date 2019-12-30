package com.example.online_shop_mobile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class DetailActivity : AppCompatActivity(), Callback<Product> {

    private var product: Product? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val id = intent.getIntExtra("idProduct", 0)

        if (id > 0) {
            ProductAPI.instance.get(id).enqueue(this)
        }
    }

    override fun onResponse(call: Call<Product>, response: Response<Product>) {
        product = response.body()
        Log.i(TAG, "Got result: $product")

        if (response.isSuccessful) {
            description.text = product?.description
            tvName.text = product?.title
        } else {
            val errorMessage = try {
                "An error occurred: ${response.errorBody()!!.string()}"
            } catch (e: IOException) {
                "An error occurred: error while decoding the error message."
            }

            Log.e(TAG, errorMessage)
            description.text = errorMessage
        }
    }

    override fun onFailure(call: Call<Product>, t: Throwable) {
        Log.w(TAG, "Error: ${t.message}", t)
    }

    companion object {
        private val TAG = DetailActivity::class.java.canonicalName
    }
}
