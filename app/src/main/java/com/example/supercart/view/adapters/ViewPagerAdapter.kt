package com.example.ecomerceapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.ecomerceapp.models.Product

class ViewPagerAdapter(
    val fragments : List<Fragment>,
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
): FragmentStateAdapter(fragmentManager,lifecycle) {

    private var androidList: ArrayList<Product>? = null
    override fun getItemCount(): Int {
        return  fragments.size
    }



    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }


}