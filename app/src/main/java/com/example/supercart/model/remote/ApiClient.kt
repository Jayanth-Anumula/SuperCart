package com.example.ecomerceapp.models

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiClient {

    val retrofit : Retrofit by lazy{

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttp = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

        Retrofit.Builder().apply {
            baseUrl("https://apolisrises.co.in/myshop/index.php/")
            client(okHttp)
            addConverterFactory(GsonConverterFactory.create())
        }.build()

    }

}