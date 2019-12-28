package com.example.online_shop_mobile


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import java.util.*

class ProductAdapter(context: Context) : ArrayAdapter<Product>(context, 0, ArrayList()) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Check if an existing view is being reused, otherwise inflate the view
        val view = if (convertView == null)
            LayoutInflater.from(context).inflate(R.layout.product_element, parent, false)
        else
            convertView

        val tvTitle = view.findViewById<TextView>(R.id.tvName)
        val tvPrice = view.findViewById<TextView>(R.id.tvPrice)


        val product = getItem(position)

        tvTitle.text = product?.title
        tvPrice.text = String.format(Locale.ENGLISH, "%.2f EUR", product?.price)

        return view
    }
}
