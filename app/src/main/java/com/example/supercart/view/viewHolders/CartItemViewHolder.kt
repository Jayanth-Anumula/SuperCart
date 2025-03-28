package com.example.supercart.view.viewHolders

import androidx.recyclerview.widget.RecyclerView
import com.example.supercart.R
import com.example.supercart.databinding.ViewHolderCartItemsBinding
import com.example.supercart.model.tables.UserCartItem
import com.squareup.picasso.Picasso

class CartItemViewHolder(val binding: ViewHolderCartItemsBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(android: UserCartItem){

        with(binding){
            androidNameId.text = "Name : ${android.product_name}"
            unitPriceId.text = "Price : $ ${android.price}"
            quantityId.text = "Quantity : ${android.quantity}"
            Picasso.get().load("https://apolisrises.co.in/myshop/images/"+android.product_image_url).placeholder(R.drawable.ic_launcher_foreground).into(androidMobileImage)
            val total = android.quantity.toInt() * android.price.toInt()
            binding.totalAmtId.text = "Total Amount : ${total}"
        }
    }
}