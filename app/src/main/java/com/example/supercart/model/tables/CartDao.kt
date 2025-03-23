package com.example.supercart.model.tables

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface CartDao {

    @Insert
    fun insertUser(user: User)

    @Insert
    fun insertProduct(product: Product)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCartItem(cartItem: CartItem)

    @Query("SELECT * FROM product")
    fun getAllProducts(): List<Product>

    @Query("UPDATE user SET isLogin = :isLogin WHERE email_id = :email AND password = :password")
    fun updateUserLoginStatus(email: String, password: String, isLogin: Boolean)

    @Query("SELECT * FROM user WHERE email_id = :email AND password = :password LIMIT 1")
    fun getUserByEmailAndPassword(email: String, password: String): User?

    @Transaction
    @Query("SELECT * FROM product WHERE product_id IN (SELECT product_id FROM cart_item WHERE user_id = :userId)")
    fun getProductsInCart(userId: Long): List<Product>

    @Query("SELECT quantity FROM cart_item WHERE user_id = :userId AND product_id = :productId")
    fun getProductQuantityInCart(userId: Long, productId: Long): Int


    @Query("SELECT user_id FROM user WHERE email_id = :email AND password = :password LIMIT 1")
    fun getUserIdByEmailAndPassword(email: String, password: String): Long?


}
