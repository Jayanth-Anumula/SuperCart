package com.example.ecomerceapp.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecomerceapp.adapters.AndroidAdapter
import com.example.ecomerceapp.models.Product
import com.example.supercart.R
import com.example.supercart.databinding.FragmentAndroidBinding
import com.example.supercart.model.tables.AppDatabase
import com.example.supercart.model.tables.CartDao
import com.example.supercart.model.tables.CartItem
import com.example.supercart.model.tables.LocalRepository
import com.example.supercart.view.CartActivity
import com.example.supercart.view.UserPreferences
import com.example.supercart.view.fragments.ProductDetailsFragment
import com.example.supercart.viewModel.CartSingleton
import com.example.supercart.viewModel.CartViewModel
import com.example.supercart.viewModel.CartViewModelFactory

import com.example.supercart.viewModel.UserViewModel

class AndroidFragment : Fragment() {

    private lateinit var binding: FragmentAndroidBinding
    private var androidList: ArrayList<Product>? = null
    var categoryDataListener: CategoryDataListener? = null
    private lateinit var adapter: AndroidAdapter
    private lateinit var userPreferences: UserPreferences
    private lateinit var localRepository: LocalRepository
    private lateinit var cartDao: CartDao
    private lateinit var cartViewModel: CartViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is CategoryDataListener) {
            categoryDataListener = context
        } else {
            throw RuntimeException("$context must implement CategoryDataListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAndroidBinding.inflate(inflater, container, false)

        userPreferences = UserPreferences(requireActivity())
        var appDatabase = AppDatabase.getInstance(requireActivity())
        cartDao = appDatabase.cartDao()
        localRepository = LocalRepository(appDatabase)
        val factory = CartViewModelFactory(localRepository)
        cartViewModel = ViewModelProvider(this, factory)[CartViewModel::class.java]

        setupRecyclerView()


//        androidList = categoryDataListener?.getAndroidList()
//        androidList?.let { updateData(it) }


        return binding.root
    }

    private fun setupRecyclerView() {
        binding.rvAndroid.layoutManager = LinearLayoutManager(requireContext())
        binding.rvAndroid.adapter = AndroidAdapter(ArrayList())
    }

    fun updateData(newList: ArrayList<Product>) {
        androidList = newList
        Log.d("newList",newList.toString())
        adapter = AndroidAdapter(newList)
        binding.rvAndroid.adapter = adapter

        adapter.setOnItemClickListener { productId ->
            val fragment = ProductDetailsFragment()
            val bundle = Bundle()
            bundle.putString("product_id", productId)
            fragment.arguments = bundle

            parentFragmentManager.beginTransaction()
                .replace(R.id.mainContainer3, fragment)
                .addToBackStack(null)
                .commit()
        }


        adapter.setAddProductToCart { product, position ->
            val userId = cartViewModel.getUserIdByEmailAndPassword(
                userPreferences.getUserEmail()!!,
                userPreferences.getUserPassword()!!
            )

            if (userId != null) {
                cartViewModel.getAllProducts()
                val allProducts = cartViewModel.productsList.value
                val existingProduct = allProducts?.firstOrNull { it.product_id.toInt() == product.product_id.toInt() }

                Log.d("ExistingProduct", existingProduct.toString())

                if (existingProduct != null) {
                    cartViewModel.addProductToCart(
                        userPreferences.getUserEmail()!!,
                        userPreferences.getUserPassword()!!,
                        existingProduct
                    )
                } else {
                    val newProduct = com.example.supercart.model.tables.Product(
                        product.average_rating,
                        product.description,
                        product.price,
                        product.product_id.toLong(),
                        product.product_image_url,
                        product.product_name
                    )
                    cartViewModel.insertProduct(newProduct)

                    val cartItem = CartItem(userId, newProduct.product_id, 1)
                    cartViewModel.insertCartItem(cartItem)
                }
            }
        }



    }

    interface CategoryDataListener {
        fun getAndroidList(): ArrayList<Product>?
    }
}
