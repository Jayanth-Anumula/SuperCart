package com.example.supercart.viewModel
import com.example.ecomerceapp.models.Product

object CartSingleton {
    private val cartList: HashMap<Product, Int> = HashMap()


    fun addProductToCart(product: Product) {
        if (cartList.containsKey(product)) {
            cartList[product] = cartList[product]?.plus(1) ?: 1
        } else {
            cartList[product] = 1
        }
    }


    fun removeProductFromCart(product: Product) {
        if (cartList.containsKey(product)) {
            val currentCount = cartList[product] ?: 0
            if (currentCount > 1) {
                cartList[product] = currentCount - 1
            } else {
                cartList.remove(product)
            }
        }
    }


    fun getCartItems(): HashMap<Product, Int> {
        return cartList
    }


    fun getProductCount(product: Product): Int {
        return cartList[product] ?: 0
    }


    fun clearCart() {
        cartList.clear()
    }
}
