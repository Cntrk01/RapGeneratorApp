package com.okation.aivideocreator.ui.prompt

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class PromptViewPager (fragmentManager: FragmentManager, lifecycle: Lifecycle, private val list : List<Fragment>)
    : FragmentStateAdapter(fragmentManager,lifecycle) {
    override fun getItemCount(): Int {
        return  list.size
    }
    override fun createFragment(position: Int): Fragment {
        return list[position]
    }
}