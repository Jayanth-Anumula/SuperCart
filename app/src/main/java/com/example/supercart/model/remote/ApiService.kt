package com.example.ecomerceapp.models


import com.example.ecomerceapp.models.AndroidResponseBody
import com.example.supercart.model.local.CategoriesResponse
import com.example.supercart.model.local.subCategoryResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("User/register")
     @Headers("Content-Type: application/json")
    suspend fun registerUser(
        @Body registerUserBody: RegisterUserBody
    ):Response<RegisterUserResponse>

    @POST("User/auth")
    @Headers("Content-Type: application/json")
    suspend fun loginUser(
        @Body loginRequestBody: LoginRequestBody
    ):Response<LoginResponseBody>


    @GET("Category")
    suspend fun categoriesList():Response<CategoriesResponse>

     @GET("SubCategory")
     suspend fun subCategoriesList(
         @Query("category_id") category_id:String
     ): Response<subCategoryResponse>


    @GET("SubCategory/products/{sub_category_id}")
    @Headers("Content-Type: application/json")
    suspend fun androidPhonesList(
        @Path ("sub_category_id") sub_category_id: String
    ):Response<AndroidResponseBody>

}