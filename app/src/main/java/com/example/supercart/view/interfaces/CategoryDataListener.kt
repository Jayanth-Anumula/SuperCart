package com.example.ecomerceapp.interfaces

import com.example.ecomerceapp.models.Product

interface CategoryDataListener {
    fun getAndroidList(): ArrayList<Product>?
}