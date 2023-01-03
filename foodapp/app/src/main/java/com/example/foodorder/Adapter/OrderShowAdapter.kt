package com.example.foodorder.Adapter

import android.graphics.Color
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorder.FoodModel.Foodlist
import com.example.foodorder.OrderModel.Orderlist
import com.example.foodorder.R

class OrderShowAdapter(private var list: List<Orderlist?>?):
    RecyclerView.Adapter<OrderShowAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderShowAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.ordershow_item, parent, false)

        return OrderShowAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderShowAdapter.ViewHolder, position: Int) {

        var item = list?.get(position)

        holder.orderid.text= item!!.orderid.toString()
        holder.price.text=item!!.price.toString()
        holder.paymentType.text=item!!.payment.toString()
        if(item.transaction.equals("null")) {
            holder.transactionId.text = "Not Available"
        }else{
            holder.transactionId.text = item.transaction.toString()
        }

        if(holder.llfood.tag=="0") {
            for (i in 0 until item.food.size) {
                var foodname = TextView(holder.llfood.context)
                foodname.setLayoutParams(
                    LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                )
                var param = foodname.layoutParams as ViewGroup.MarginLayoutParams
                param.setMargins(10, 5, 8, 8)
                foodname.layoutParams = param

                val fnameQty = item.food[i].foodname + " (" + item.food[i].quantity + "pcs)"

                foodname.text = fnameQty
                foodname.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16F)
                foodname.setTextColor(Color.parseColor("#FF5722"))
                holder.llfood.addView(foodname)
                holder.llfood.tag = "1"

            }
        }





    }

    override fun getItemCount(): Int {
       return list!!.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        var orderid: TextView =itemView.findViewById(R.id.orderid)
        var paymentType: TextView =itemView.findViewById(R.id.paymentType)
        var price: TextView =itemView.findViewById(R.id.price)
        var transactionId: TextView =itemView.findViewById(R.id.transactionId)
        var llfood: LinearLayout =itemView.findViewById(R.id.llfood)
    }
}