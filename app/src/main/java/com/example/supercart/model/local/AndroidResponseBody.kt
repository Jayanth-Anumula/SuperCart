package com.example.ecomerceapp.models

import com.google.gson.annotations.SerializedName

class AndroidResponseBody(

    val message: String,
    val products: List<Product>,
    val status: Int
) {
}