package com.example.supercart.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.supercart.databinding.ActivityCartBinding
import com.example.supercart.databinding.FragmentCartBinding
import com.example.supercart.model.tables.AppDatabase
import com.example.supercart.model.tables.LocalRepository
import com.example.supercart.model.tables.UserCartItem
import com.example.supercart.view.CheckoutActivity
import com.example.supercart.view.UserPreferences
import com.example.supercart.view.adapters.CartAdapter
import com.example.supercart.view.adapters.CartFragmentAdapter
import com.example.supercart.viewModel.CartViewModel
import com.example.supercart.viewModel.CartViewModelFactory

class CartFragment:Fragment() {

    private lateinit var binding: FragmentCartBinding
    private lateinit var adapter: CartFragmentAdapter
    private lateinit var viewModel: CartViewModel
    private lateinit var localRepository: LocalRepository
    private lateinit var userPreferences: UserPreferences
    private lateinit var cartList: List<UserCartItem>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var appDatabase = AppDatabase.getInstance(requireActivity())
        localRepository = LocalRepository(appDatabase)
        var factory = CartViewModelFactory(localRepository)
        viewModel = ViewModelProvider(this,factory)[CartViewModel::class.java]
        userPreferences = UserPreferences(requireActivity())
        var userId = viewModel.getUserIdByEmailAndPassword(userPreferences.getUserEmail()!!,userPreferences.getUserPassword()!!)
        if (userId != null) {
            viewModel.getUserCartItems(userId.toLong())
        }
        binding = FragmentCartBinding.inflate(LayoutInflater.from(requireContext()),container,false)
        setUpObservers()

        binding.nextBtn.setOnClickListener(){
            (binding.root.context as? CheckoutActivity)?.goToStep(1)

        }
        return binding.root
    }

    private fun setUpObservers() {
        viewModel.cartItems.observe(requireActivity()){
            var totalAmt = 0
            var totalCheckoutItems =0
            cartList = it
            cartList.map {
                totalCheckoutItems += it.quantity
                totalAmt += it.quantity.toInt()*it.price.toInt()
            }

            adapter = CartFragmentAdapter(it)
            binding.rvCartItems.layoutManager = LinearLayoutManager(requireActivity())
            binding.rvCartItems.adapter = adapter
            binding.totalBillAmt.text = "Total Bill Amount : $$totalAmt"

        }

        viewModel.error.observe(requireActivity()){
            Toast.makeText(requireActivity(),it.toString(), Toast.LENGTH_LONG)
        }
    }
}