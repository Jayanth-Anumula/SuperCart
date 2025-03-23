package com.example.supercart.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.ecomerceapp.models.ApiClient
import com.example.ecomerceapp.models.ApiService
import com.example.ecomerceapp.models.RegisterUserBody
import com.example.supercart.R
import com.example.supercart.commons.ApiResult
import com.example.supercart.databinding.ActivityMainBinding
import com.example.supercart.model.remote.UserRepository
import com.example.supercart.viewModel.UserViewModel
import com.example.supercart.viewModel.UserViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var userPreferences: UserPreferences
    private lateinit var viewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        intializeViewModel()
        userPreferences = UserPreferences(this)
        setUpObservers()

        binding.registerBtn.setOnClickListener {
            val name = binding.nameEt.text.toString().trim()
            val mobile = binding.mobileNoEt.text.toString().trim()
            val email = binding.emailEt.text.toString().trim()
            val password = binding.passwordEt.text.toString().trim()

            if (name.isNotEmpty() && mobile.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                viewModel.registerUser(RegisterUserBody(name, mobile, email, password))
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }


        }


    }

    private fun setUpObservers() {
        viewModel.apiResult.observe(this) { result ->
            when (result) {
                is ApiResult.Loading -> {
                    binding.registerBtn.isEnabled = false
                    Toast.makeText(this, "Registering user...", Toast.LENGTH_SHORT).show()
                }
                is ApiResult.Success -> {
                    Toast.makeText(this@MainActivity, "Registration Successful!", Toast.LENGTH_LONG).show()
                    val name = binding.nameEt.text.toString().trim()
                    val mobile = binding.mobileNoEt.text.toString().trim()
                    val email = binding.emailEt.text.toString().trim()
                    val password = binding.passwordEt.text.toString().trim()
                    startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                    finish()

                }
                is ApiResult.Error -> {
                    binding.registerBtn.isEnabled = true
                    Toast.makeText(this, "Error: ${result.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun intializeViewModel() {
        var repo = UserRepository(ApiClient.retrofit.create(ApiService::class.java))
        var factory = UserViewModelFactory(repo)
        viewModel = ViewModelProvider(this,factory)[UserViewModel::class.java]
    }
}