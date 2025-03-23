package com.example.supercart.model.remote

import com.example.ecomerceapp.models.ApiService
import com.example.ecomerceapp.models.LoginRequestBody
import com.example.ecomerceapp.models.RegisterUserBody

class UserRepository(private val apiService: ApiService) {

    suspend fun registerUser(registerUserBody: RegisterUserBody) = apiService.registerUser(registerUserBody)

    suspend fun loginUser(loginRequestBody: LoginRequestBody) = apiService.loginUser(loginRequestBody)
}