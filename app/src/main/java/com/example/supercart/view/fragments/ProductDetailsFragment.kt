package com.example.supercart.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecomerceapp.models.ApiClient
import com.example.ecomerceapp.models.ApiService
import com.example.supercart.databinding.FragmentProductDetailsBinding
import com.example.supercart.model.local.ProductDetails
import com.example.supercart.model.remote.ProductRepository
import com.example.supercart.view.adapters.ProductImagesAdapter

import com.example.supercart.viewModel.PhonesViewModel
import com.example.supercart.viewModel.PhonesViewModelFactory

class ProductDetailsFragment : Fragment() {

    private lateinit var binding: FragmentProductDetailsBinding

    private lateinit var viewModel: PhonesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val productId = arguments?.getString("product_id") ?: return

        val repo = ProductRepository(ApiClient.retrofit.create(ApiService::class.java))
        viewModel = ViewModelProvider(requireActivity(), PhonesViewModelFactory(repo))[PhonesViewModel::class.java]

        viewModel.getProductDetails(productId)

        viewModel.apiProductDetailsResult.observe(viewLifecycleOwner) { productDetails ->
            bindProductDetails(productDetails)
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun bindProductDetails(product: ProductDetails) {
        val productData = product.product

        // Basic info
        binding.tvProductTitle.text = productData.product_name
        binding.tvDescription.text = productData.description
        binding.tvPrice.text = "$${productData.price}"
        binding.ratingBar.rating = productData.average_rating.toFloatOrNull() ?: 0f

        // Image ViewPager setup
        val imageUrls = productData.images.map { "https://apolisrises.co.in/myshop/images/products/${it.image}" }
        val imageAdapter = ProductImagesAdapter(imageUrls)
        binding.viewPagerImages.adapter = imageAdapter

        // Specification views
        binding.specsContainer.removeAllViews()
        for (spec in productData.specifications) {
            val textView = TextView(requireContext())
            textView.text = "${spec.title}: ${spec.specification}"
            textView.setPadding(8, 4, 8, 4)
            binding.specsContainer.addView(textView)
        }


    }
}
