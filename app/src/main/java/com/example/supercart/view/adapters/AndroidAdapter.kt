package com.example.ecomerceapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ecomerceapp.models.Product
import com.example.ecomerceapp.viewHolders.AndroidViewHolder
import com.example.supercart.databinding.ViewHolderAndroidBinding

class AndroidAdapter(var androidList: List<Product>):RecyclerView.Adapter<AndroidViewHolder> (){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AndroidViewHolder {
        var binding = ViewHolderAndroidBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AndroidViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return androidList.size
    }

    override fun onBindViewHolder(holder: AndroidViewHolder, position: Int) {
        holder.bind(androidList[position])

        holder.binding.addCartBtn.setOnClickListener(){
            if(::addProductToCart.isInitialized){
                addProductToCart(androidList[position],position)
            }
        }
    }

    private lateinit var addProductToCart:(product:Product,position:Int) -> Unit

    fun setAddProductToCart(l:(product:Product,position:Int) -> Unit ){
        addProductToCart = l
    }
}