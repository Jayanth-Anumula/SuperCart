package com.example.supercart.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecomerceapp.adapters.AndroidAdapter
import com.example.ecomerceapp.models.Product
import com.example.supercart.R
import com.example.supercart.databinding.ActivityCartBinding
import com.example.supercart.viewModel.CartSingleton

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private lateinit var adapter: AndroidAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        setUpObservers()
    }

    private fun setUpObservers() {

    }
}
