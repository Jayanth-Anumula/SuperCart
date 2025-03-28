package com.example.supercart.model.remote

import com.example.ecomerceapp.models.ApiService
import com.example.ecomerceapp.models.LoginRequestBody
import com.example.ecomerceapp.models.RegisterUserBody
import com.example.supercart.model.local.Address
import com.example.supercart.model.local.OrderRequestBody

class UserRepository(private val apiService: ApiService) {

    suspend fun registerUser(registerUserBody: RegisterUserBody) = apiService.registerUser(registerUserBody)

    suspend fun loginUser(loginRequestBody: LoginRequestBody) = apiService.loginUser(loginRequestBody)

    suspend fun getAllAddress(userId:String) = apiService.getAllAddress(userId)
    suspend fun postAddress(address: Address) = apiService.postAddress(address)

    suspend fun postOrders(orderRequestBody: OrderRequestBody) = apiService.postOrder(orderRequestBody)
    suspend fun getAllOrders(userId: String) = apiService.getAllOrders(userId)


}