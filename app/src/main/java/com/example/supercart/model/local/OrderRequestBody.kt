package com.example.supercart.model.local

data class OrderRequestBody(
    val bill_amount: Int,
    val delivery_address: DeliveryAddress,
    val items: List<Item>,
    val payment_method: String,
    val user_id: Int
)