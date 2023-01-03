package com.example.foodorder.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorder.Common.cms
import com.example.foodorder.FoodModel.CartModel
import com.example.foodorder.FoodModel.Foodlist
import com.example.foodorder.Interface.DeleteItem
import com.example.foodorder.Interface.FoodQuantityChange
import com.example.foodorder.R

class CartAdapter(
    private var list: ArrayList<CartModel>,
    var foodQuantityChange: FoodQuantityChange,
    var deleteItem: DeleteItem
) :
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    var boolDataChanged :Boolean =false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      cms.cartQty.add("1")

        if(boolDataChanged){
             holder.qty.text="1"
        }

        val item = list?.get(position)
        holder.cartFoodName.text = item.foodName.toString()
        holder.cartFoodPrice.text = item.foodPrice.toString()

        var c = 1
        holder.qtyPlus.setOnClickListener {
            c++
            holder.qty.text = c.toString()
            var updatePrice = item.foodPrice?.toInt()?.times(c)
            holder.cartFoodPrice.text = updatePrice.toString()

            foodQuantityChange.onChangePrice(position, holder.cartFoodPrice.text.toString().toInt())


           var cartMdl = CartModel()
            cartMdl.id=item.id
            cartMdl.foodPrice=item.foodPrice
            cartMdl.foodName=item.foodName
            cartMdl.foodQty= holder.qty.text.toString().toInt()

            cms.myList.set(position,cartMdl)

        }
        holder.qtyMinus.setOnClickListener {
            if (c == 1) {

            } else {
                c--
                holder.qty.text = c.toString()

                var insPrice = holder.cartFoodPrice.text.toString().toInt()
                holder.cartFoodPrice.text = (insPrice - item.foodPrice!!.toInt()).toString()
                foodQuantityChange.onChangePrice(
                    position,
                    holder.cartFoodPrice.text.toString().toInt()
                )

                var cartMdl = CartModel()
                cartMdl.id=item.id
                cartMdl.foodPrice=item.foodPrice
                cartMdl.foodName=item.foodName
                cartMdl.foodQty= holder.qty.text.toString().toInt()

                cms.myList.set(position,cartMdl)

            }
        }

        holder.deleteItem.setOnClickListener {
            val dialog = AlertDialog.Builder(holder.deleteItem.context)
            dialog.setMessage("Want To Remove From Cart ?")
            dialog.setPositiveButton(android.R.string.yes) { dialog, which ->
               deleteItem.delete(position)
            }
            dialog.setNegativeButton(android.R.string.no) { dialog, which ->
                dialog.dismiss()
            }


            dialog.show()
        }


    }

    fun updateList(newList: ArrayList<CartModel>) {
        list = newList
        boolDataChanged=true
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }


    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        var cartFoodName: TextView = itemView.findViewById(R.id.cartFoodName)
        var cartFoodPrice: TextView = itemView.findViewById(R.id.cartFoodPrice)
        var qtyPlus: ImageView = itemView.findViewById(R.id.qtyPlus)
        var qtyMinus: ImageView = itemView.findViewById(R.id.qtyMinus)
        var qty: TextView = itemView.findViewById(R.id.qty)
        var deleteItem: ImageView = itemView.findViewById(R.id.deleteItem)
    }
}