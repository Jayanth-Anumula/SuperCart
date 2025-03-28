package com.example.supercart.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecomerceapp.models.ApiClient
import com.example.ecomerceapp.models.ApiService
import com.example.supercart.databinding.FragmentSummaryBinding
import com.example.supercart.model.local.AddressData
import com.example.supercart.model.local.DeliveryAddress
import com.example.supercart.model.local.Item
import com.example.supercart.model.local.OrderRequestBody
import com.example.supercart.model.remote.UserRepository
import com.example.supercart.model.tables.AppDatabase
import com.example.supercart.model.tables.LocalRepository
import com.example.supercart.model.tables.UserCartItem
import com.example.supercart.view.UserPreferences
import com.example.supercart.view.adapters.CartFragmentAdapter
import com.example.supercart.viewModel.CartViewModel
import com.example.supercart.viewModel.CartViewModelFactory
import com.example.supercart.viewModel.UserViewModel
import com.example.supercart.viewModel.UserViewModelFactory

class SummaryFragment : Fragment() {

    private lateinit var binding: FragmentSummaryBinding
    private lateinit var cartViewModel: CartViewModel
    private lateinit var userViewModel: UserViewModel
    private lateinit var userPreferences: UserPreferences
    private lateinit var adapter: CartFragmentAdapter
    private lateinit var cartList: List<UserCartItem>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSummaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val appDatabase = AppDatabase.getInstance(requireContext())
        val localRepo = LocalRepository(appDatabase)
        val cartFactory = CartViewModelFactory(localRepo)
        cartViewModel = ViewModelProvider(requireActivity(), cartFactory)[CartViewModel::class.java]

        val userRepo = UserRepository(ApiClient.retrofit.create(ApiService::class.java))
        val userFactory = UserViewModelFactory(userRepo)
        userViewModel = ViewModelProvider(requireActivity(), userFactory)[UserViewModel::class.java]

        userPreferences = UserPreferences(requireContext())
        var userId = cartViewModel.getUserIdByEmailAndPassword(userPreferences.getUserEmail()!!,userPreferences.getUserPassword()!!)
        if (userId != null) {
            cartViewModel.getUserCartItems(userId.toLong())
        }
        setUpObservers()
        val selectedAddress = parentFragmentManager.fragments.find { it is DeliveryFragment } as? DeliveryFragment
        val address = selectedAddress?.getSelectedAddress()

        val paymentFragment = parentFragmentManager.fragments.find { it is PayemtFragment } as? PayemtFragment
        val paymentMethod = paymentFragment?.getSelectedPaymentMethod()
        Log.d("paymentMethod22",paymentMethod.toString())

         cartViewModel.selectedPaymentMethod.observe(requireActivity()){

             binding.tvPaymentMethod.text = it

        }
        binding.tvDeliveryAddress.text = """
            ${address?.title}
            ${address?.address}
        """.trimIndent()


        binding.btnConfirmOrder.setOnClickListener {
            placeOrder()
        }
    }

    private fun setUpObservers() {
        cartViewModel.cartItems.observe(requireActivity()){
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
            binding.tvTotalAmountLabel.text = "Total Bill Amount : $$totalAmt"

        }

        cartViewModel.error.observe(requireActivity()){
            Toast.makeText(requireActivity(),it.toString(), Toast.LENGTH_LONG)
        }
    }

    private fun placeOrder() {
        var userId = cartViewModel.getUserIdByEmailAndPassword(userPreferences.getUserEmail()!!,userPreferences.getUserPassword()!!)
        if (userId != null) {
            cartViewModel.getUserCartItems(userId.toLong())
        }


        val selectedAddress = parentFragmentManager.fragments.find { it is DeliveryFragment } as? DeliveryFragment
        val address = selectedAddress?.getSelectedAddress()

        val paymentFragment = parentFragmentManager.fragments.find { it is PayemtFragment } as? PayemtFragment
        val paymentMethod = paymentFragment?.getSelectedPaymentMethod()

        if (address == null || paymentMethod.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Missing address or payment method", Toast.LENGTH_SHORT).show()
            return
        }

        val cartItems = userId?.let { cartViewModel.getUserCartItems(it.toLong()) }
        if (cartItems != null) {
            if (cartItems.isEmpty()) {
                Toast.makeText(requireContext(), "Cart is empty", Toast.LENGTH_SHORT).show()
                return
            }
        }

        val itemList = cartItems!!.map {
            Item(
                product_id = it.product_id.toInt(),
                quantity = it.quantity,
                unit_price = it.price.toInt()
            )
        }

        val totalBill = itemList.sumOf { it.unit_price * it.quantity }

        val deliveryAddress = DeliveryAddress(
            title = address.title,
            address = address.address
        )

        val order = userId?.let {
            OrderRequestBody(
                user_id = userPreferences.getUserId()!!.toInt(),
                bill_amount = totalBill,
                delivery_address = deliveryAddress,
                payment_method = cartViewModel.selectedPaymentMethod.value!!,
                items = itemList
            )
        }

        if (order != null) {
            userViewModel.postOrder(order)
        }

        userViewModel.postOrder.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        }

        userViewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        }
    }
}
