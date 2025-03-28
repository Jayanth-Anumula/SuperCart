package com.example.supercart.view

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ecomerceapp.adapters.ViewPagerAdapter
import com.example.supercart.R
import com.example.supercart.databinding.ActivityCheckoutBinding
import com.example.supercart.view.fragments.CartFragment
import com.example.supercart.view.fragments.DeliveryFragment
import com.example.supercart.view.fragments.PayemtFragment
import com.example.supercart.view.fragments.SummaryFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class CheckoutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheckoutBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpPageViewer()


    }
    fun goToStep(step: Int) {
        binding.viewPager.setCurrentItem(step, true)
    }



    private fun setUpPageViewer() {
        val fragmentsList = listOf(
            CartFragment(),
            DeliveryFragment(),
            PayemtFragment(),
            SummaryFragment()
        )

        viewPagerAdapter = ViewPagerAdapter(fragmentsList, supportFragmentManager, lifecycle)
        binding.viewPager.adapter = viewPagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Cart Items"
                1 -> "Delivery"
                2 -> "Payment"
                3 -> "Summary"
                else -> ""
            }
        }.attach()

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val position = tab?.position ?: return
                Log.d("Checkout Activity", "Tab selected: $position")

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }


}