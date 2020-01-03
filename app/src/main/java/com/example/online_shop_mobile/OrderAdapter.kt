package com.example.online_shop_mobile


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.online_shop_mobile.pojo.Order
import java.util.*

class OrderAdapter(context: Context) : ArrayAdapter<Order>(context, 0, ArrayList()) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Check if an existing view is being reused, otherwise inflate the view
        val view = if (convertView == null)
            LayoutInflater.from(context).inflate(R.layout.order_element, parent, false)
        else
            convertView

        val orderNum = view.findViewById<TextView>(R.id.tvOrder)
        val orderStatus = view.findViewById<TextView>(R.id.tvStatus)

        val order = getItem(position)

        orderNum.text = "Order #" + order?.id.toString()

        when(order?.status) {
            0 -> {
                orderStatus.text = "Confirmed"
                orderStatus.setTextColor(0xff2ECC71.toInt())
            }
            1 -> {
                orderStatus.text = "Pending"
                orderStatus.setTextColor(0xff3498DB.toInt())
            }
            2 -> {
                orderStatus.text = "Declined"
                orderStatus.setTextColor(0xffE74C3C.toInt())
            }
        }

        return view
    }
}
