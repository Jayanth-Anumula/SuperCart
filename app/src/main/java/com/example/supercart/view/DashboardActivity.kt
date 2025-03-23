package com.example.supercart.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.supercart.R
import com.example.supercart.databinding.ActivityDashboardBinding
import com.example.supercart.model.tables.AppDatabase
import com.example.supercart.model.tables.CartDao
import com.example.supercart.model.tables.LocalRepository
import com.example.supercart.viewModel.CartViewModel
import com.example.supercart.viewModel.CartViewModelFactory
import com.squareup.picasso.Picasso

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var userPreferences: UserPreferences
    private lateinit var cartDao: CartDao
    private lateinit var cartViewModel: CartViewModel
    private lateinit var localRepository: LocalRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        userPreferences = UserPreferences(this)
        var appDatabase = AppDatabase.getInstance(this)
        cartDao = appDatabase.cartDao()
        localRepository = LocalRepository(appDatabase)
        val factory = CartViewModelFactory(localRepository)
        cartViewModel = ViewModelProvider(this, factory)[CartViewModel::class.java]


        Picasso.get().load("https://apolisrises.co.in/myshop/images/smartphones.png").into(binding.phonesImage)
        Picasso.get().load("https://apolisrises.co.in/myshop/images/laptops.png").into(binding.laptopImage)
        Picasso.get().load("https://apolisrises.co.in/myshop/images/mensfashion.png").into(binding.MensWearImage)
        Picasso.get().load("https://apolisrises.co.in/myshop/images/womensfashion.png").into(binding.womensWearImage)
        Picasso.get().load("https://apolisrises.co.in/myshop/images/kidsfashion.png").into(binding.kidsWearImage)
        Picasso.get().load("https://apolisrises.co.in/myshop/images/homeappliances.png").into(binding.groceryImage)


        binding.phonesImage.setOnClickListener(){
            val i = Intent(this,CategoryActivity::class.java)
            startActivity(i)
        }

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
                    Toast.makeText(this,"Home Clicked", Toast.LENGTH_SHORT).show()
                }
                R.id.cart -> {
                    val intent = Intent(this, CartActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this,"Cart Clicked", Toast.LENGTH_SHORT).show()
                }
                R.id.orders -> {
                    Toast.makeText(this,"orders Clicked", Toast.LENGTH_SHORT).show()
                }
                R.id.profile -> {
                    Toast.makeText(this,"Profile Clicked", Toast.LENGTH_SHORT).show()
                }
                R.id.logout -> {
                    logOutUser()
                }
            }

            binding.drawerLayout.closeDrawers()
            true
        }

    }

    private fun logOutUser() {
        cartViewModel.logoutUser(userPreferences.getUserEmail()!!,userPreferences.getUserPassword()!!)
        userPreferences.logoutUser()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)

    }
}
