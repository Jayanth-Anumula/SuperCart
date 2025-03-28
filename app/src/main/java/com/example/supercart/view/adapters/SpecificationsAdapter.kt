package com.example.supercart.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.supercart.databinding.ViewHolderSpecBinding
import com.example.supercart.model.local.Specification
import com.example.supercart.view.viewHolders.SpecificationViewHolder

class SpecificationsAdapter(private var specs: List<Specification>) : RecyclerView.Adapter<SpecificationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecificationViewHolder {
        val binding = ViewHolderSpecBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SpecificationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SpecificationViewHolder, position: Int) {
        holder.bind(specs[position])
    }

    override fun getItemCount(): Int = specs.size

    fun updateData(newSpecs: List<Specification>) {
        specs = newSpecs
        notifyDataSetChanged()
    }
}
