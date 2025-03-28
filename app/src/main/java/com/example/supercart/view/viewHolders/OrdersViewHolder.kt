package com.example.supercart.view.viewHolders

import androidx.recyclerview.widget.RecyclerView
import com.example.supercart.databinding.ViewHolderOrderBinding
import com.example.supercart.model.local.Order

class OrdersViewHolder(private val binding: ViewHolderOrderBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(order: Order) {
        binding.tvOrderId.text = "Order ID: #${order.order_id}"
        binding.tvOrderDate.text = "Date: ${order.order_date}"
        binding.tvOrderAmount.text = "Amount: $${order.bill_amount}"
        binding.tvPaymentMethod.text = "Payment: ${order.payment_method}"
        binding.tvDeliveryAddress.text = "Deliver to: ${order.address_title}, ${order.address}"
        binding.tvStatus.text = "Status: ${order.order_status}"
    }
}
