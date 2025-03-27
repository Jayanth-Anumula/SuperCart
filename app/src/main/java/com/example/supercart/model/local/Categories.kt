package com.example.supercart.model.local

import com.google.gson.annotations.SerializedName

data class Categories(
    @SerializedName("category_id")
    val category_id: String,

    @SerializedName("category_name")
    val category_name: String,

    @SerializedName("category_image_url")
    val category_image_url: String,

    @SerializedName("is_active")
    val is_active: String
)
