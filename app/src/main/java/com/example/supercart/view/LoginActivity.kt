package com.example.supercart.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.supercart.databinding.ActivityLoginBinding
import com.example.supercart.model.remote.UserRepository
import com.example.supercart.viewModel.UserViewModel
import com.example.supercart.viewModel.UserViewModelFactory
import com.example.ecomerceapp.models.ApiClient
import com.example.ecomerceapp.models.ApiService
import com.example.ecomerceapp.models.LoginRequestBody
import com.example.supercart.commons.ApiResult
import com.example.supercart.model.tables.User

import com.example.supercart.model.tables.AppDatabase
import com.example.supercart.model.tables.CartDao
import com.example.supercart.model.tables.LocalRepository
import com.example.supercart.viewModel.CartViewModel
import com.example.supercart.viewModel.CartViewModelFactory

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var userPreferences: UserPreferences
    private lateinit var localRepository: LocalRepository
    private lateinit var viewModel: UserViewModel
    private lateinit var cartDao: CartDao
    private lateinit var cartViewModel: CartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeViewModel()
        userPreferences = UserPreferences(this)
        var appDatabase = AppDatabase.getInstance(this)
        cartDao = appDatabase.cartDao()
        localRepository = LocalRepository(appDatabase)
        val factory = CartViewModelFactory(localRepository)
        cartViewModel = ViewModelProvider(this, factory)[CartViewModel::class.java]

        viewModel.apiLoginResult.observe(this, Observer { result ->
            when (result) {
                is ApiResult.Loading -> {
                    binding.loginBtn.isEnabled = false
                    Toast.makeText(this, "Processing user...", Toast.LENGTH_SHORT).show()
                }
                is ApiResult.Success -> {
                    val loginResponse = result.data
                    if (loginResponse.status == 0 && loginResponse.user != null) {

                        val email = binding.loginEmailEt.text.toString().trim()
                        val password = binding.loginPasswordEt.text.toString().trim()


                        cartViewModel.loginUser(email, password)

                        userPreferences.saveUser(email, password)
                        userPreferences.setLoggedIn(true)

                        Toast.makeText(this, "User Logged In", Toast.LENGTH_LONG).show()
                        startActivity(Intent(this, DashboardActivity::class.java))
                    } else {
                        Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show()
                    }
                }
                is ApiResult.Error -> {
                    binding.loginBtn.isEnabled = true
                    Toast.makeText(this, "Error: ${result.message}", Toast.LENGTH_LONG).show()
                }
            }
        })

        binding.loginBtn.setOnClickListener {
            val email = binding.loginEmailEt.text.toString().trim()
            val password = binding.loginPasswordEt.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                val loginRequestBody = LoginRequestBody(email, password)
                viewModel.loginUser(loginRequestBody)
            } else {
                Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show()
            }
        }

        binding.registerText.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun initializeViewModel() {
        val repo = UserRepository(ApiClient.retrofit.create(ApiService::class.java))
        val factory = UserViewModelFactory(repo)
        viewModel = ViewModelProvider(this, factory)[UserViewModel::class.java]
    }
}
