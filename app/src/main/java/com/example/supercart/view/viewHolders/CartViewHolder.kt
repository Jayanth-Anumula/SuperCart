package com.example.supercart.view.viewHolders

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.supercart.R
import com.example.supercart.databinding.ViewHolderAndroidBinding
import com.example.supercart.model.tables.UserCartItem
import com.squareup.picasso.Picasso

class CartViewHolder(var binding: ViewHolderAndroidBinding):RecyclerView.ViewHolder(binding.root) {

    fun bind(android: UserCartItem){

        with(binding){
            androidNameId.text = "Name : ${android.product_name}"
            androidDescriptionId.text = android.description
            priceId.text = "Price : $ ${android.price}"
            androidRatingId.text = "Rating : ${android.average_rating}"
            addCartBtn.text = "Items : ${android.quantity}"
            Picasso.get().load("https://apolisrises.co.in/myshop/images/"+android.product_image_url).placeholder(
                R.drawable.ic_launcher_foreground).into(androidMobileImage)
        }
    }
}