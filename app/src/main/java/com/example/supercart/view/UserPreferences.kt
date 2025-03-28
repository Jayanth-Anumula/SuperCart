package com.example.supercart.view

import android.content.Context
import android.content.SharedPreferences

class UserPreferences(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)



    fun saveUser(userId:String,email: String, password: String) {
        val editor = sharedPreferences.edit()
        editor.putString("userId",userId)
        editor.putString("email", email)
        editor.putString("password", password)
        editor.putBoolean("isLoggedIn", true)
        editor.apply()
    }

    fun getUserEmail(): String? {
        return sharedPreferences.getString("email", "User")
    }
    fun getUserPassword(): String? {
        return sharedPreferences.getString("password", "User")
    }
    fun getUserId(): String? {
        return sharedPreferences.getString("userId", "1")
    }

    fun loginUser(userId:String,email: String, password: String): Boolean {

        val savedEmail = sharedPreferences.getString("email", null)
        val savedPassword = sharedPreferences.getString("password", null)
        return if (email == savedEmail && password == savedPassword) {
            setLoggedIn(true)
            true
        } else {
            false
        }
    }

    fun setLoggedIn(value: Boolean) {
        sharedPreferences.edit().putBoolean("isLoggedIn", value).apply()
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean("isLoggedIn", false)
    }

    fun logoutUser() {
        setLoggedIn(false)
    }

    fun getUserName(): String? {
        return sharedPreferences.getString("name", "User")
    }
}
