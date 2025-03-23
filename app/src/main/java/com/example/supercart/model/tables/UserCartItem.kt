package com.example.supercart.model.tables

data class UserCartItem(
    val product_id: Long,
    val product_name: String,
    val description: String,
    val price: String,
    val average_rating: String,
    val product_image_url: String,
    val quantity: Int
)
