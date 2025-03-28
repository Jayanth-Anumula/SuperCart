package com.example.ecomerceapp.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ecomerceapp.models.Product
import com.example.ecomerceapp.viewHolders.AndroidViewHolder
import com.example.supercart.databinding.ViewHolderAndroidBinding
import com.example.supercart.view.ProductDetailsActivity

class AndroidAdapter(var androidList: List<Product>) : RecyclerView.Adapter<AndroidViewHolder>() {

    private var onItemClick: ((productId: String) -> Unit)? = null
    private var addProductToCart: ((product: Product, position: Int) -> Unit)? = null

    fun setOnItemClickListener(listener: (productId: String) -> Unit) {
        onItemClick = listener
    }

    fun setAddProductToCart(listener: (product: Product, position: Int) -> Unit) {
        addProductToCart = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AndroidViewHolder {
        val binding = ViewHolderAndroidBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AndroidViewHolder(binding)
    }

    override fun getItemCount(): Int = androidList.size

    override fun onBindViewHolder(holder: AndroidViewHolder, position: Int) {
        val product = androidList[position]
        holder.bind(product)



        holder.binding.androidDescriptionId.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, ProductDetailsActivity::class.java)
            intent.putExtra("product_id", androidList[position].product_id.toString())
            context.startActivity(intent)
        }


        holder.binding.addCartBtn.setOnClickListener {
            addProductToCart?.invoke(product, position)
        }
    }
}
