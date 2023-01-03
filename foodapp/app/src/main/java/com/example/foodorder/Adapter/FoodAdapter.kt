package com.example.foodorder.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.convertTo
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodorder.FoodModel.Foodlist
import com.example.foodorder.Interface.AddToCart
import com.example.foodorder.R

class FoodAdapter(private var list: List<Foodlist?>?, var addtocart:AddToCart) : RecyclerView.Adapter<FoodAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.itemfood, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var item = list?.get(position)
        Glide
            .with(holder.foodImage.context)
            .load(item?.imgurl)
            .fitCenter()
            .into(holder.foodImage);


        holder.nameFood.text= item?.foodName?.capitalize()
        holder.priceFood.text=item?.foodPrice

        Log.e("img url",""+item?.imgurl)

        holder.addCartBtn.setOnClickListener {

            addtocart.onClick(item?.id , item?.foodName , item?.foodPrice)

        }

    }

    fun updateList(newList: List<Foodlist?>) {
        list = newList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
      var foodImage:ImageView=itemView.findViewById(R.id.foodImage)
      var nameFood:TextView=itemView.findViewById(R.id.nameFood)
      var priceFood:TextView=itemView.findViewById(R.id.priceFood)
      var addCartBtn:TextView=itemView.findViewById(R.id.addCartBtn)
    }

}

