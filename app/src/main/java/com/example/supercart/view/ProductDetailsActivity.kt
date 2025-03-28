package com.example.supercart.view



import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.ecomerceapp.models.ApiClient
import com.example.ecomerceapp.models.ApiService
import com.example.supercart.databinding.ActivityProductDetailsBinding
import com.example.supercart.model.local.ProductDetails
import com.example.supercart.model.remote.ProductRepository
import com.example.supercart.view.adapters.ProductImagesAdapter

import com.example.supercart.viewModel.PhonesViewModel
import com.example.supercart.viewModel.PhonesViewModelFactory

class ProductDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailsBinding
    private lateinit var viewModel: PhonesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val productId = intent.getStringExtra("product_id") ?: return

        val repo = ProductRepository(ApiClient.retrofit.create(ApiService::class.java))
        viewModel = ViewModelProvider(this, PhonesViewModelFactory(repo))[PhonesViewModel::class.java]

        viewModel.getProductDetails(productId)

        viewModel.apiProductDetailsResult.observe(this) {
            bindProductDetails(it)
        }

        viewModel.error.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun bindProductDetails(product: ProductDetails) {
        val p = product.product

        binding.tvProductTitle.text = p.product_name
        binding.tvDescription.text = p.description
        binding.tvPrice.text = "$${p.price}"
        binding.ratingBar.rating = p.average_rating.toFloatOrNull() ?: 0f

        binding.viewPagerImages.adapter = ProductImagesAdapter(p.images.map { it.image })

        binding.specsContainer.removeAllViews()
        p.specifications.forEach {
            val spec = TextView(this)
            spec.text = "${it.title}: ${it.specification}"
            spec.setPadding(8, 4, 8, 4)
            binding.specsContainer.addView(spec)
        }

//        val reviewAdapter = ProductReviewsAdapter(p.reviews)
//        binding.recyclerReviews.layoutManager = LinearLayoutManager(this)
//        binding.recyclerReviews.adapter = reviewAdapter
    }
}
