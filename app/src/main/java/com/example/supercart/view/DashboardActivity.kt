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
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ecomerceapp.models.ApiClient
import com.example.ecomerceapp.models.ApiService
import com.example.supercart.R
import com.example.supercart.adapters.CategoryAdapter
import com.example.supercart.commons.ApiResult
import com.example.supercart.databinding.ActivityDashboardBinding
import com.example.supercart.model.local.Categories
import com.example.supercart.model.remote.ProductRepository
import com.example.supercart.model.tables.AppDatabase
import com.example.supercart.model.tables.CartDao
import com.example.supercart.model.tables.LocalRepository
import com.example.supercart.viewModel.CartViewModel
import com.example.supercart.viewModel.CartViewModelFactory
import com.example.supercart.viewModel.PhonesViewModel
import com.example.supercart.viewModel.PhonesViewModelFactory
import com.squareup.picasso.Picasso

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var userPreferences: UserPreferences
    private lateinit var cartDao: CartDao
    private lateinit var cartViewModel: CartViewModel
    private lateinit var localRepository: LocalRepository
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var phonesViewModel: PhonesViewModel
    private lateinit var categories: List<Categories>


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

        var productRepository = ProductRepository(ApiClient.retrofit.create(ApiService::class.java))
        val phonesFactory = PhonesViewModelFactory(productRepository)
        phonesViewModel = ViewModelProvider(this, phonesFactory)[PhonesViewModel::class.java]


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
                    val intent = Intent(this, OrdersActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this,"Orders Clicked", Toast.LENGTH_SHORT).show()

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

        observeCategoryList()
        phonesViewModel.categoriesList()

    }

    private fun observeCategoryList() {
        phonesViewModel.apiCategoriesResult.observe(this) { result ->
            when (result) {
                is ApiResult.Loading -> Toast.makeText(this, "Loading categories...", Toast.LENGTH_SHORT).show()
                is ApiResult.Error -> Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
                is ApiResult.Success -> {
                     categories = result.data.categories
                    setupCategoryRecyclerView()

                }
            }
        }
    }

    private fun setupCategoryRecyclerView() {
       if(::categories.isInitialized){
           categoryAdapter = CategoryAdapter(categories)
           binding.rvCategories.layoutManager = GridLayoutManager(this, 2)
           binding.rvCategories.adapter = categoryAdapter
       }

        categoryAdapter.setOnClickCategoryItemListner { category: Categories, _: Int ->
            val intent = Intent(this, CategoryActivity::class.java)
            intent.putExtra("categoryId", category.category_id)
            startActivity(intent)
        }
    }

    private fun logOutUser() {
        cartViewModel.logoutUser(userPreferences.getUserEmail()!!,userPreferences.getUserPassword()!!)
        userPreferences.logoutUser()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)

    }
}
