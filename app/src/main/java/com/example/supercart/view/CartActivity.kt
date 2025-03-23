package com.example.supercart.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecomerceapp.adapters.AndroidAdapter
import com.example.ecomerceapp.models.Product
import com.example.supercart.R
import com.example.supercart.databinding.ActivityCartBinding
import com.example.supercart.model.tables.AppDatabase
import com.example.supercart.model.tables.LocalRepository
import com.example.supercart.model.tables.UserCartItem
import com.example.supercart.view.adapters.CartAdapter
import com.example.supercart.viewModel.CartSingleton
import com.example.supercart.viewModel.CartViewModel
import com.example.supercart.viewModel.CartViewModelFactory

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private lateinit var adapter: CartAdapter
    private lateinit var viewModel: CartViewModel
    private lateinit var localRepository:LocalRepository
    private lateinit var userPreferences: UserPreferences
    private lateinit var cartList: List<UserCartItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var appDatabase = AppDatabase.getInstance(this)
        localRepository = LocalRepository(appDatabase)
        var factory = CartViewModelFactory(localRepository)
        viewModel = ViewModelProvider(this,factory)[CartViewModel::class.java]
        userPreferences = UserPreferences(this)



        setSupportActionBar(binding.toolbar)

        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.open,
            R.string.close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    Toast.makeText(this, "Home Clicked", Toast.LENGTH_SHORT).show()
                }
                R.id.cart -> {
                    val intent = Intent(this, CartActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "Cart Clicked", Toast.LENGTH_SHORT).show()
                }
                R.id.orders -> {
                    Toast.makeText(this, "orders Clicked", Toast.LENGTH_SHORT).show()
                }
                R.id.profile -> {
                    Toast.makeText(this, "Profile Clicked", Toast.LENGTH_SHORT).show()
                }
                R.id.logout -> {
                }
            }

            binding.drawerLayout.closeDrawers()
            true
        }
        var userId = viewModel.getUserIdByEmailAndPassword(userPreferences.getUserEmail()!!,userPreferences.getUserPassword()!!)
        if (userId != null) {
            viewModel.getUserCartItems(userId.toLong())
        }

        setUpObservers()
    }

    private fun setUpObservers() {

        Log.d("cartItems4",viewModel.cartItems.toString())

        viewModel.cartItems.observe(this){
            var totalAmt = 0
            var totalCheckoutItems =0
            cartList = it
            cartList.map {
                totalCheckoutItems += it.quantity
                totalAmt += it.quantity.toInt()*it.price.toInt()
            }

            adapter = CartAdapter(it)
            binding.rvCartItems.layoutManager = LinearLayoutManager(this)
            binding.rvCartItems.adapter = adapter
            binding.totalAmtTv.text = "Total Items : $$totalAmt"
            binding.checkOutBtn.text = "Total Checkout Items : $totalCheckoutItems"
        }

        viewModel.error.observe(this){
            Toast.makeText(this,it.toString(),Toast.LENGTH_LONG)
        }



    }
}
