package com.example.ecomerceapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.supercart.databinding.FragmentWindowsBinding

class WindowsFragment : Fragment() {

    private lateinit var binding: FragmentWindowsBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         binding = FragmentWindowsBinding.inflate(layoutInflater)
        return binding.root
    }
}