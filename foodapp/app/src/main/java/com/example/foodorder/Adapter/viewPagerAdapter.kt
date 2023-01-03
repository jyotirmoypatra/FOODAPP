package com.example.foodorder.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.foodorder.Fragment.accountFragment
import com.example.foodorder.Fragment.orderFragment

class viewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm)  {
    override fun getCount(): Int {
        return 2;
    }

    override fun getItem(position: Int): Fragment {
        when(position) {
            0 -> {
                return accountFragment()
            }
            1 -> {
                return orderFragment()
            }
            else -> {
                return accountFragment()
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position) {
            0 -> {
                return "ACCOUNT"
            }
            1 -> {
                return "ORDER"
            }

        }
        return super.getPageTitle(position)
    }
}