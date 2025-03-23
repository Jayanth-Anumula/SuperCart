package com.example.ecomerceapp.models

import com.google.gson.annotations.SerializedName

data class LoginResponseBody(
    @SerializedName("status")
    val status:Int,
    @SerializedName("message")
    val message:String,
    @SerializedName("user")
    val user:User?,

) {
}