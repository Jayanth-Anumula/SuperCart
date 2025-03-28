package com.example.supercart.model.local

data class OrdersResponse(
    val message: String,
    val orders: List<Order>,
    val status: Int
)