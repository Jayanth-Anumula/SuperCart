package com.example.ecomerceapp.models

import com.google.gson.annotations.SerializedName

data class RegisterUserResponse(
    @SerializedName("status")
    val status:Int,
    @SerializedName("message")
    val message:String
) {
}