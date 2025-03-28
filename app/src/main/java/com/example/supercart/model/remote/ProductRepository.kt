package com.example.supercart.model.remote

import com.example.ecomerceapp.models.ApiService
import com.example.supercart.model.local.Address

class ProductRepository(private val apiService: ApiService) {

    suspend fun androidPhonesList( sub_category_id : String) = apiService.androidPhonesList(sub_category_id)

    suspend fun categoriesList() = apiService.categoriesList()

    suspend fun subCategoriesList(category_id:String) = apiService.subCategoriesList(category_id)
<<<<<<< HEAD

    suspend fun getProductDetails(product_id:String) = apiService.getProductDetails(product_id)
=======
>>>>>>> aeeaa65a9144bd1c849947c46287c10a1fbc0b02
}