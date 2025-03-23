package com.example.supercart.model.tables

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
data class Product(
    val average_rating: String,
    val description: String,
    val price: String,
    @PrimaryKey(autoGenerate = true)
    val product_id: Long=0,
    val product_image_url: String,
    val product_name: String
)
