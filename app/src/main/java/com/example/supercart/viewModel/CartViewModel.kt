package com.example.supercart.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.supercart.model.tables.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartViewModel(private val repository: LocalRepository) : ViewModel() {

    private val _cartItems = MutableLiveData<List<CartItem>>()
    val cartItems: LiveData<List<CartItem>> = _cartItems

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error
    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    private val _productsList = repository.getAllProducts()
    val productsList: LiveData<List<Product>> = _products

    fun insertUser(user: User) {

            try {
                repository.insertUser(user)
            } catch (e: Exception) {
                _error.postValue(e.toString())
            }

    }

    fun getAllProducts() {

            try {
                val allProducts = repository.getAllProducts()
                _products.postValue(allProducts)
            } catch (e: Exception) {
                _error.postValue(e.toString())
            }

    }

    fun getUserIdByEmailAndPassword(email: String, password: String): Long? {
        var userId: Long?
        userId = null

        try {
                val user = repository.getUserByEmailAndPassword(email, password)
                Log.d("user", user.toString())
                userId = user?.user_id
            } catch (e: Exception) {
                _error.postValue(e.toString())
            }

        return repository.getUserIdByEmailAndPassword(email,password)
    }

    fun addProductToCart(email: String, password: String, product: Product) {
            try {
                val userId = repository.getUserIdByEmailAndPassword(email, password)

                if (userId != null) {
                    val currentQuantity = repository.getProductQuantityInCart(userId, product.product_id)

                    if (currentQuantity > 0) {
                        Log.d("currentQuantity", currentQuantity.toString())
                        val updatedCartItem = CartItem(user_id = userId, product_id = product.product_id, quantity = currentQuantity + 1)
                        repository.insertCartItem(updatedCartItem)
                        return
                    } else {
                        Log.d("currentQuantity", currentQuantity.toString())
                        val newCartItem = CartItem(user_id = userId, product_id = product.product_id, quantity = 1)
                        repository.insertCartItem(newCartItem)
                        return
                    }
                } else {
                    _error.postValue("User not found")
                }
            } catch (e: Exception) {
                _error.postValue(e.toString())
            }

    }

    fun loginUser(email: String, password: String) {

            try {
                var existingUser = repository.getUserByEmailAndPassword(email, password)
                if (existingUser != null) {
                    repository.updateUserLoginStatus(email, password, true)
                } else {
                    val newUser = User(email_id = email, password = password, isLogin = true)
                    repository.insertUser(newUser)
                    existingUser = newUser

                    _error.postValue("User not found")
                }
            } catch (e: Exception) {
                Log.d("ErrorMsg", e.toString())
                _error.postValue(e.toString())
            }
        }


    fun logoutUser(email: String, password: String) {

            try {
                repository.updateUserLoginStatus(email, password, false)
            } catch (e: Exception) {
                _error.postValue(e.toString())
            }

    }

    fun insertProduct(product: Product) {

            try {
                repository.insertProduct(product)
            } catch (e: Exception) {
                _error.postValue(e.toString())
            }

    }

    fun insertCartItem(cartItem: CartItem) {

            try {
                repository.insertCartItem(cartItem)
            } catch (e: Exception) {
                _error.postValue(e.toString())
            }

    }




}

class CartViewModelFactory(private val repository: LocalRepository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CartViewModel(repository) as T
    }
}
