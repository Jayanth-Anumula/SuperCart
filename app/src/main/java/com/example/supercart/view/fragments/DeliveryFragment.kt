package com.example.supercart.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecomerceapp.models.ApiClient
import com.example.ecomerceapp.models.ApiService
import com.example.supercart.databinding.DialogAddAddressBinding
import com.example.supercart.databinding.FragmentDeliveryBinding
import com.example.supercart.model.local.Address
import com.example.supercart.model.local.AddressData
import com.example.supercart.model.remote.UserRepository
import com.example.supercart.model.tables.AppDatabase
import com.example.supercart.model.tables.LocalRepository
import com.example.supercart.view.CheckoutActivity
import com.example.supercart.view.UserPreferences
import com.example.supercart.view.adapters.AddressAdapter
import com.example.supercart.viewModel.CartViewModel
import com.example.supercart.viewModel.CartViewModelFactory
import com.example.supercart.viewModel.UserViewModel
import com.example.supercart.viewModel.UserViewModelFactory

class DeliveryFragment : Fragment() {

    private lateinit var binding: FragmentDeliveryBinding
    private lateinit var userviewModel: UserViewModel
    private lateinit var addressAdapter: AddressAdapter
    private var addressList: List<AddressData> = mutableListOf()
    private var selectedAddress: AddressData? = null
    private lateinit var userRepository: UserRepository
    private lateinit var userPreferences: UserPreferences
    private lateinit var viewModel: CartViewModel
    private lateinit var localRepository: LocalRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDeliveryBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        userRepository = UserRepository(ApiClient.retrofit.create(ApiService::class.java))
        var factory = UserViewModelFactory(userRepository)
        userviewModel = ViewModelProvider(this,factory)[UserViewModel::class.java]
        var appDatabase = AppDatabase.getInstance(requireActivity())
        localRepository = LocalRepository(appDatabase)
        var cartfactory = CartViewModelFactory(localRepository)
        viewModel = ViewModelProvider(this,cartfactory)[CartViewModel::class.java]
        userPreferences = UserPreferences(requireActivity())
        var userId = viewModel.getUserIdByEmailAndPassword(userPreferences.getUserEmail()!!,userPreferences.getUserPassword()!!)
        if (userId != null) {

            userviewModel.getAllAddress("444")

        }
        fetchAddresses()





        binding.btnAddAddress.setOnClickListener {
            showAddAddressDialog()
        }

        binding.btnNext.setOnClickListener {
            selectedAddress?.let {
                val checkoutActivity = activity as? CheckoutActivity
                if (checkoutActivity != null) {
                    checkoutActivity.goToStep(2)
                }
                Log.d("SelectedAddress", it.toString())
            } ?: Toast.makeText(requireContext(), "Please select an address", Toast.LENGTH_SHORT).show()
        }
    }



    private fun setupRecyclerView() {
        addressAdapter = AddressAdapter(emptyList()) { address ->
            selectedAddress = address
        }
        binding.rvAddresses.layoutManager = LinearLayoutManager(requireContext())
        binding.rvAddresses.adapter = addressAdapter
    }

    private fun fetchAddresses() {
        userviewModel.getAllAddress("444") // Replace with actual userId
        userviewModel.addressList.observe(viewLifecycleOwner) {
            addressList = it.addresses
            addressAdapter.updateData(addressList)
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }
    fun getSelectedAddress(): AddressData? = selectedAddress


    private fun showAddAddressDialog() {
        val dialogBinding = DialogAddAddressBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(requireActivity())
            .setView(dialogBinding.root)
            .create()

        dialogBinding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialogBinding.btnSave.setOnClickListener {
            val title = dialogBinding.etTitle.text.toString()
            val address = dialogBinding.etAddress.text.toString()

            if (title.isNotEmpty() && address.isNotEmpty()) {
                val newAddress = userPreferences.getUserId()?.let { it1 ->
                    Address(
                        user_id = it1.toInt(),
                        title = title,
                        address = address
                    )
                }
                if (newAddress != null) {
                    userviewModel.postAddress(newAddress)
                }

                userviewModel.apiPostAddress.observe(viewLifecycleOwner) {
                    fetchAddresses()
                    dialog.dismiss()
                }
            } else {
                Toast.makeText(requireActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }
}