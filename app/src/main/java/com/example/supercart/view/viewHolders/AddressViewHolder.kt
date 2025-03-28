package com.example.supercart.view.viewHolders


import androidx.recyclerview.widget.RecyclerView
import com.example.supercart.databinding.ItemAddressBinding
import com.example.supercart.model.local.Address
import com.example.supercart.model.local.AddressData

class AddressViewHolder(val binding: ItemAddressBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(address: AddressData, isSelected: Boolean) {
        binding.tvTitle.text = address.title
        binding.tvAddress.text = address.address
        binding.radioSelected.isChecked = isSelected
    }
}
