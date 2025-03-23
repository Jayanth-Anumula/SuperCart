package com.example.ecomerceapp.viewHolders

import android.util.Log
import androidx.recyclerview.widget.RecyclerView

import com.example.ecomerceapp.models.Product
import com.example.supercart.R
import com.example.supercart.databinding.ViewHolderAndroidBinding
import com.squareup.picasso.Picasso

class AndroidViewHolder(var binding: ViewHolderAndroidBinding):RecyclerView.ViewHolder(binding.root) {


    fun bind(android:Product){
        with(binding){
            androidNameId.text = "Name : ${android.product_name}"
            androidDescriptionId.text = android.description
            priceId.text = "Price : $ ${android.price}"
            androidRatingId.text = "Rating : ${android.average_rating}"
            Log.d("images","https://apolisrises.co.in/myshop/images/"+android.product_image_url)
            Picasso.get().load("https://apolisrises.co.in/myshop/images/"+android.product_image_url).placeholder(
                R.drawable.ic_launcher_foreground).into(androidMobileImage)
        }
    }
}