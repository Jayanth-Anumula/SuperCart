package com.example.supercart.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.supercart.databinding.ItemCategoryBinding
import com.example.supercart.model.local.Categories

class CategoryAdapter(
    private var categories: List<Categories>
) : RecyclerView.Adapter<CategoryViewHolder>() {

    private lateinit var onClickCategoryItemListener: (Categories, Int) -> Unit

    fun setOnClickCategoryItemListner(listener: (Categories, Int) -> Unit) {
        onClickCategoryItemListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCategoryBinding.inflate(inflater, parent, false)
        return CategoryViewHolder(binding)
    }

    override fun getItemCount(): Int = categories.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position], onClickCategoryItemListener)
    }
}
