package com.example.tabunganimpian

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 2 // Jumlah halaman
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FirstPageFragment()
            else -> SecondPageFragment()
        }
    }
}
