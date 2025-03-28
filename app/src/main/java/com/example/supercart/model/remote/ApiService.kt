package com.example.ecomerceapp.models


import com.example.ecomerceapp.models.AndroidResponseBody
<<<<<<< HEAD
import com.example.supercart.model.local.Address
import com.example.supercart.model.local.AddressResponse
import com.example.supercart.model.local.CategoriesResponse
import com.example.supercart.model.local.OrderRequestBody
import com.example.supercart.model.local.OrderResponse
import com.example.supercart.model.local.OrdersResponse
import com.example.supercart.model.local.ProductDetails
=======
import com.example.supercart.model.local.CategoriesResponse
>>>>>>> aeeaa65a9144bd1c849947c46287c10a1fbc0b02
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

<<<<<<< HEAD
    @GET("User/addresses/{user_id}")
    suspend fun getAllAddress(
        @Path("user_id") user_id:String
    ): Response<AddressResponse>

    @POST("User/address")
    @Headers("Content-Type: application/json")
    suspend fun postAddress(
        @Body address: Address
    ):Response<RegisterUserResponse>
=======
>>>>>>> aeeaa65a9144bd1c849947c46287c10a1fbc0b02

    @GET("SubCategory/products/{sub_category_id}")
    @Headers("Content-Type: application/json")
    suspend fun androidPhonesList(
        @Path ("sub_category_id") sub_category_id: String
    ):Response<AndroidResponseBody>

    @POST("Order")
    @Headers("Content-Type: application/json")
    suspend fun postOrder(
        @Body orderRequestBody: OrderRequestBody
    ):Response<OrderResponse>

    @GET("Order/userOrders/{user_id}")
    suspend fun getAllOrders(
        @Path("user_id") user_id: String
    ):Response<OrdersResponse>

    @GET("Product/details/{product_id}")
    suspend fun getProductDetails(
        @Path("product_id") product_id: String
    ):Response<ProductDetails>



}