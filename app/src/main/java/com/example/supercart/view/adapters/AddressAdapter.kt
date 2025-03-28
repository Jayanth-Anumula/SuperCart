package com.example.supercart.view.adapters

import com.example.supercart.view.viewHolders.AddressViewHolder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.supercart.databinding.ItemAddressBinding
import com.example.supercart.model.local.Address
import com.example.supercart.model.local.AddressData


class AddressAdapter(
    private var items: List<AddressData>,
    private var selectedIndex: Int = -1,
    private val onItemSelected: (AddressData) -> Unit
) : RecyclerView.Adapter<AddressViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        val binding = ItemAddressBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddressViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        holder.bind(items[position], position == selectedIndex)

        holder.binding.radioSelected.setOnClickListener {
            selectedIndex = position
            notifyDataSetChanged()
            onItemSelected(items[position])
        }
    }

    override fun getItemCount(): Int = items.size

    fun updateData(newItems: List<AddressData>) {
        items = newItems
        notifyDataSetChanged()
    }
}
