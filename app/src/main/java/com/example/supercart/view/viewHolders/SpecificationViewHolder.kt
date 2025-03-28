package com.example.supercart.view.viewHolders

import androidx.recyclerview.widget.RecyclerView
import com.example.supercart.databinding.ViewHolderSpecBinding
import com.example.supercart.model.local.Specification

class SpecificationViewHolder(val binding: ViewHolderSpecBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(spec: Specification) {
        binding.tvSpecTitle.text = spec.title
        binding.tvSpecValue.text = spec.specification
    }
}
