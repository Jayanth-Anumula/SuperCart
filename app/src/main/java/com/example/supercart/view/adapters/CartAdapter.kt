package com.example.supercart.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ecomerceapp.viewHolders.AndroidViewHolder
import com.example.supercart.databinding.ViewHolderAndroidBinding
import com.example.supercart.model.tables.UserCartItem
import com.example.supercart.view.viewHolders.CartViewHolder

class CartAdapter(var cartItemList:List<UserCartItem>):RecyclerView.Adapter<CartViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        var binding = ViewHolderAndroidBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CartViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return cartItemList.size
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
       holder.bind(cartItemList[position])
    }


}