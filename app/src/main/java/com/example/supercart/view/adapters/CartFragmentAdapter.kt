package com.example.supercart.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.supercart.databinding.FragmentCartBinding
import com.example.supercart.databinding.ViewHolderAndroidBinding
import com.example.supercart.databinding.ViewHolderCartItemsBinding
import com.example.supercart.model.tables.UserCartItem
import com.example.supercart.view.viewHolders.CartItemViewHolder
import com.example.supercart.view.viewHolders.CartViewHolder

class CartFragmentAdapter(var cartItemList:List<UserCartItem>): RecyclerView.Adapter<CartItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        var binding = ViewHolderCartItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CartItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return cartItemList.size
    }

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        holder.bind(cartItemList[position])
    }


}