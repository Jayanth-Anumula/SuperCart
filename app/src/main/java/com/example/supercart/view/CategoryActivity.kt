package com.example.supercart.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.ecomerceapp.adapters.ViewPagerAdapter
import com.example.ecomerceapp.fragments.AndroidFragment
import com.example.ecomerceapp.fragments.IphonesFragment
import com.example.ecomerceapp.fragments.WindowsFragment
import com.example.ecomerceapp.interfaces.CategoryDataListener
import com.example.ecomerceapp.models.AndroidResponseBody
import com.example.ecomerceapp.models.ApiClient
import com.example.ecomerceapp.models.ApiService
import com.example.ecomerceapp.models.Product
import com.example.supercart.R
import com.example.supercart.commons.ApiResult
import com.example.supercart.databinding.ActivityCategoryBinding
import com.example.supercart.databinding.ActivityLoginBinding
import com.example.supercart.model.remote.ProductRepository
import com.example.supercart.model.remote.UserRepository
import com.example.supercart.viewModel.PhonesViewModel
import com.example.supercart.viewModel.PhonesViewModelFactory
import com.example.supercart.viewModel.UserViewModel
import com.example.supercart.viewModel.UserViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CategoryActivity : AppCompatActivity(), CategoryDataListener,
    AndroidFragment.CategoryDataListener {

    private lateinit var binding: ActivityCategoryBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private var androidList: ArrayList<Product>? = null
    lateinit var viewModel: PhonesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        intializeViewModel()
        setUpPageViewer()
        fetchDataForSubCategory(0)
    }

    private fun intializeViewModel() {
        val repo = ProductRepository(ApiClient.retrofit.create(ApiService::class.java))
        val factory = PhonesViewModelFactory(repo)
        viewModel = ViewModelProvider(this, factory)[PhonesViewModel::class.java]

    }

    private fun setUpPageViewer() {
        val fragmentsList = listOf(
            AndroidFragment().apply { categoryDataListener = this@CategoryActivity },
            IphonesFragment(),
            WindowsFragment()
        )

        viewPagerAdapter = ViewPagerAdapter(fragmentsList, supportFragmentManager, lifecycle)
        binding.viewPager.adapter = viewPagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Android"
                1 -> "Iphones"
                2 -> "Windows"
                else -> ""
            }
        }.attach()

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val position = tab?.position ?: return
                Log.d("CategoryActivity", "Tab selected: $position")  // Add this log
                fetchDataForSubCategory(position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }

    private fun fetchDataForSubCategory(position: Int) {
        val subCategoryId = when (position) {
            0 -> "1"
            1 -> "2"
            2 -> "3"
            else -> return
        }
        if(subCategoryId.isNotEmpty()){
            viewModel.androidPhonesList(subCategoryId)
        }else{
            viewModel.androidPhonesList("1")
        }
        viewModel.apiPhoneResult.observe(this){
            when(it){
                is ApiResult.Error -> {
                    Log.d("CategoryActivity", " ${it.message}")
                    Toast.makeText(this@CategoryActivity, " ${it.message}", Toast.LENGTH_LONG).show()

                }
                ApiResult.Loading -> {
                    Toast.makeText(this@CategoryActivity, "Data Loading", Toast.LENGTH_LONG).show()
                }
                is ApiResult.Success -> {
                    androidList = ArrayList(it.data.products)
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
