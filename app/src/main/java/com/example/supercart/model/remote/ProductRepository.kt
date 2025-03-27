package com.example.supercart.model.remote

import com.example.ecomerceapp.models.ApiService

class ProductRepository(private val apiService: ApiService) {

    suspend fun androidPhonesList( sub_category_id : String) = apiService.androidPhonesList(sub_category_id)

    suspend fun categoriesList() = apiService.categoriesList()

    suspend fun subCategoriesList(category_id:String) = apiService.subCategoriesList(category_id)
}