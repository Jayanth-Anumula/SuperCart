package com.example.supercart.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.supercart.databinding.FragmentPaymentBinding
import com.example.supercart.model.tables.AppDatabase
import com.example.supercart.model.tables.LocalRepository
import com.example.supercart.view.CheckoutActivity
import com.example.supercart.viewModel.CartViewModel
import com.example.supercart.viewModel.CartViewModelFactory

class PayemtFragment : Fragment() {

    private lateinit var binding: FragmentPaymentBinding
    private var selectedPayment: String? = null // Nullable & safe
    private lateinit var cartViewModel: CartViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPaymentBinding.inflate(inflater, container, false)
        val appDatabase = AppDatabase.getInstance(requireContext())
        val localRepo = LocalRepository(appDatabase)
        val cartFactory = CartViewModelFactory(localRepo)
        cartViewModel = ViewModelProvider(requireActivity(), cartFactory)[CartViewModel::class.java]

        binding.rgPaymentOptions.setOnCheckedChangeListener { _, checkedId ->
            val selectedRadio = view?.findViewById<RadioButton>(checkedId)
            selectedPayment = selectedRadio?.text.toString()
            cartViewModel.selectedPaymentMethod.postValue(selectedPayment)

            Log.d("PaymentMethod", "Selected: $selectedPayment")
        }

        binding.btnNextPayment.setOnClickListener {
            selectedPayment?.let {
                Log.d("PaymentMethod", "Button clicked with: $it")
                (activity as? CheckoutActivity)?.goToStep(3)
            } ?: Log.d("PaymentMethod", "No option selected")
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }

    fun getSelectedPaymentMethod(): String? = selectedPayment
}
