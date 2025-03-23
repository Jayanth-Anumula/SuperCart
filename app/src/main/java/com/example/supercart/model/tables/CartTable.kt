package com.example.supercart.model.tables

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "cart_item",
    primaryKeys = ["user_id", "product_id"],
    foreignKeys = [
        ForeignKey(entity = User::class, parentColumns = ["user_id"], childColumns = ["user_id"]),
        ForeignKey(entity = Product::class, parentColumns = ["product_id"], childColumns = ["product_id"])
    ]
)
data class CartItem(
    val user_id: Long,
    val product_id: Long,
    val quantity: Int
)
