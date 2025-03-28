package com.example.supercart.view.adapters



import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.supercart.databinding.ItemProductImageBinding

class ProductImagesAdapter(private val images: List<String>) :
    RecyclerView.Adapter<ProductImagesAdapter.ImageViewHolder>() {

    inner class ImageViewHolder(val binding: ItemProductImageBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ItemProductImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageUrl = "https://apolisrises.co.in/myshop/images/${images[position]}"
        Glide.with(holder.itemView.context)
            .load(imageUrl)
            .into(holder.binding.imageProduct)
    }
}
