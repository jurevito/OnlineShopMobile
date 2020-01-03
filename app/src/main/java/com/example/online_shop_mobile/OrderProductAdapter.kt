package com.example.online_shop_mobile


import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.online_shop_mobile.api.ProductAPI
import com.example.online_shop_mobile.pojo.Product
import com.example.online_shop_mobile.pojo.ProductOrder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.util.*
import kotlinx.android.synthetic.main.activity_order_detail.*

class OrderProductAdapter(context: Context) : ArrayAdapter<ProductOrder>(context, 0, ArrayList()) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Check if an existing view is being reused, otherwise inflate the view
        val view = if (convertView == null)
            LayoutInflater.from(context).inflate(R.layout.product_order_element, parent, false)
        else
            convertView

        val orderProductTv = view.findViewById<TextView>(R.id.orderProductTv)
        val quantity = view.findViewById<TextView>(R.id.quantity)

        val orderProduct = getItem(position)
        val id = orderProduct!!.id_product

        ProductAPI.instance.get(id).enqueue(object : Callback<Product> {

            override fun onFailure(call: Call<Product>, t: Throwable?) {
                Log.w("failure", "Error: ${t!!.message}", t)
            }

            override fun onResponse(call: Call<Product>, response: Response<Product>) {

                val product = response.body()

                if (response.isSuccessful) {
                    orderProductTv.text = product!!.title
                    quantity.text = orderProduct.amount.toString() + " x " + String.format(Locale.ENGLISH, "%.2f EUR", product!!.price)

                } else {
                    val errorMessage = try {
                        "An error occurred: ${response.errorBody()!!.string()}"
                    } catch (e: IOException) {
                        "An error occurred: error while decoding the error message."
                    }
                    Log.e("failure", errorMessage)
                }
            }
        })

        return view
    }
}
