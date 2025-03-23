package com.example.ecomerceapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.supercart.databinding.FragmentIphonesBinding

class IphonesFragment: Fragment() {

    private lateinit var binding: FragmentIphonesBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         binding = FragmentIphonesBinding.inflate(layoutInflater)
        return binding.root
    }
}