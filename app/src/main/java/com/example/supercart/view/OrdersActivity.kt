package com.example.supercart.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecomerceapp.models.ApiClient
import com.example.ecomerceapp.models.ApiService
import com.example.supercart.databinding.ActivityOrdersBinding
import com.example.supercart.model.remote.UserRepository
import com.example.supercart.view.adapters.OrdersAdapter
import com.example.supercart.viewModel.UserViewModel
import com.example.supercart.viewModel.UserViewModelFactory

class OrdersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrdersBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var ordersAdapter: OrdersAdapter
    private lateinit var userPreferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOrdersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize
        userPreferences = UserPreferences(this)

        val repo = UserRepository(ApiClient.retrofit.create(ApiService::class.java))
        val factory = UserViewModelFactory(repo)
        userViewModel = ViewModelProvider(this, factory)[UserViewModel::class.java]

        setupRecyclerView()

        val userId = userPreferences.getUserId().toString()
        userViewModel.getAllOrders(userId)

        observeOrders()
    }

    private fun setupRecyclerView() {
        ordersAdapter = OrdersAdapter(emptyList())
        binding.rvOrders.layoutManager = LinearLayoutManager(this)
        binding.rvOrders.adapter = ordersAdapter
    }

    private fun observeOrders() {
        userViewModel.ordersList.observe(this) { response ->
            val orders = response.orders
            Log.d("orders", orders.toString())
            binding.ordersCount.text = "Total Orders: ${orders.size}"
            ordersAdapter.updateData(orders) // just update existing adapter's data
        }

        userViewModel.error.observe(this) {
            Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
        }
    }

}
