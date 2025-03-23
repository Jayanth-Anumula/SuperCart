package com.example.ecomerceapp.models

import com.google.gson.annotations.SerializedName

data class LoginRequestBody(
    @SerializedName("email_id")
    val email_id : String,
    @SerializedName("password")
    val password:String

) {
}