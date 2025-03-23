package com.example.supercart.model.tables

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class LocalRepository(private val appDatabase: AppDatabase) {

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun insertUser(user: User) {
        try {
            appDatabase.cartDao().insertUser(user)
        } catch (e: Exception) {
            _error.postValue(e.toString())
        }
    }

    fun getAllProducts(): List<Product> {
        return try {
            appDatabase.cartDao().getAllProducts()
        } catch (e: Exception) {
            Log.d("ErrorMsg", e.toString())
            _error.postValue(e.toString())
            emptyList()
        }
    }

    fun updateUserLoginStatus(email: String, password: String, isLogin: Boolean) {
        try {
            appDatabase.cartDao().updateUserLoginStatus(email, password, isLogin)
        } catch (e: Exception) {
            Log.d("ErrorMsg", e.toString())
            _error.postValue(e.toString())
        }
    }

    fun getUserIdByEmailAndPassword(email: String, password: String): Long? {
        return appDatabase.cartDao().getUserIdByEmailAndPassword(email, password)
    }

    fun getUserByEmailAndPassword(email: String, password: String): User? {
        return try {
            appDatabase.cartDao().getUserByEmailAndPassword(email, password)
        } catch (e: Exception) {
            _error.postValue(e.toString())
            null
        }
    }

    fun insertProduct(product: Product) {
        try {
            appDatabase.cartDao().insertProduct(product)
        } catch (e: Exception) {
            _error.postValue(e.toString())
        }
    }

    fun insertCartItem(cartItem: CartItem) {
        try {
            appDatabase.cartDao().insertCartItem(cartItem)
        } catch (e: Exception) {
            _error.postValue(e.toString())
        }
    }



    fun getProductQuantityInCart(userId: Long, productId: Long): Int {
        return try {
            appDatabase.cartDao().getProductQuantityInCart(userId, productId)
        } catch (e: Exception) {
            _error.postValue(e.toString())
            0
        }
    }



}
