package com.example.supercart.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecomerceapp.adapters.ViewPagerAdapter
import com.example.ecomerceapp.fragments.AndroidFragment
import com.example.ecomerceapp.models.ApiClient
import com.example.ecomerceapp.models.ApiService
import com.example.ecomerceapp.models.Product
import com.example.supercart.commons.ApiResult
import com.example.supercart.databinding.ActivityCategoryBinding
import com.example.supercart.model.local.subcategories
import com.example.supercart.model.remote.ProductRepository
import com.example.supercart.model.remote.UserRepository
import com.example.supercart.viewModel.PhonesViewModel
import com.example.supercart.viewModel.PhonesViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class CategoryActivity : AppCompatActivity(), AndroidFragment.CategoryDataListener {

    private lateinit var binding: ActivityCategoryBinding
    private lateinit var viewModel: PhonesViewModel
    private var subCategories: List<subcategories> = listOf()
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private var androidList: ArrayList<Product>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val categoryId = intent.getStringExtra("categoryId")
        if (categoryId.isNullOrEmpty()) {
            Toast.makeText(this, "Invalid category ID", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        initializeViewModel()
        fetchSubCategories(categoryId)
    }

    private fun initializeViewModel() {
        val productRepository = ProductRepository(ApiClient.retrofit.create(ApiService::class.java))
        val phonesFactory = PhonesViewModelFactory(productRepository)
        viewModel = ViewModelProvider(this, phonesFactory)[PhonesViewModel::class.java]
    }

    private fun fetchSubCategories(categoryId: String) {
        viewModel.subCategoriesList(categoryId)

        viewModel.apiSubCategoriesResult.observe(this) { result ->
            when (result) {
                is ApiResult.Loading -> {
                    Toast.makeText(this, "Loading subcategories...", Toast.LENGTH_SHORT).show()
                }
                is ApiResult.Error -> {
                    Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
                }
                is ApiResult.Success -> {
                    subCategories = result.data.subcategories
                    setUpPageViewer(subCategories)

                    // Fetch data for first subcategory after tabs are set
                    if (subCategories.isNotEmpty()) {
                        fetchDataForSubCategory(subCategories[0].subcategory_id)
                    }
                }

                else -> {}
            }
        }
    }

    private fun setUpPageViewer(subCategories: List<subcategories>) {
        val fragmentsList = subCategories.map { subCategory ->
            AndroidFragment().apply {
                arguments = Bundle().apply {
                    putString("subCategoryId", subCategory.subcategory_id)
                }
                categoryDataListener = this@CategoryActivity
            }
        }

        viewPagerAdapter = ViewPagerAdapter(fragmentsList, supportFragmentManager, lifecycle)
        binding.viewPager.adapter = viewPagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = subCategories[position].subcategory_name
        }.attach()

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val position = tab?.position ?: return
                fetchDataForSubCategory(subCategories[position].subcategory_id)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun fetchDataForSubCategory(subCategoryId: String) {
        viewModel.androidPhonesList(subCategoryId)

        viewModel.apiPhoneResult.observe(this) { result ->
            when (result) {
                is ApiResult.Loading -> {
                    Toast.makeText(this, "Loading products...", Toast.LENGTH_SHORT).show()
                }
                is ApiResult.Error -> {
                    Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
                }
                is ApiResult.Success -> {
                    Log.d("Products",result.data.products.toString())
                    androidList = ArrayList(result.data.products)
                    val fragment = supportFragmentManager.fragments.find { it is AndroidFragment } as? AndroidFragment
                    fragment?.updateData(androidList!!)
                }
            }
        }
    }

    override fun getAndroidList(): ArrayList<Product>? {
        return androidList
    }
}
