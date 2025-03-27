package com.example.supercart.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.supercart.databinding.ItemCategoryBinding
import com.example.supercart.model.local.Categories
import com.squareup.picasso.Picasso

class CategoryViewHolder(private val binding: ItemCategoryBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(category: Categories, clickListener: (Categories, Int) -> Unit) {
        binding.categoryName.text = category.category_name

        Picasso.get()
            .load("https://apolisrises.co.in/myshop/images/${category.category_image_url}")
            .into(binding.categoryImage)

        binding.root.setOnClickListener {
            clickListener(category, adapterPosition)
        }
    }
}

