package com.vk59.kostylev.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vk59.kostylev.R
import com.vk59.kostylev.databinding.MainActivityBinding
import com.vk59.kostylev.ui.hot.HotFragment
import com.vk59.kostylev.ui.latest.LatestFragment
import com.vk59.kostylev.ui.top.TopFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewPager()

        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }

    private fun setupViewPager() {
        val adapter = ViewPagerAdapter(supportFragmentManager)

        adapter.addFragment(LatestFragment(), getString(R.string.latestTab))
        adapter.addFragment(TopFragment(), getString(R.string.topTab))
        adapter.addFragment(HotFragment(), getString(R.string.hotTab))
        binding.viewPager.adapter = adapter
        binding.viewPager.offscreenPageLimit = 2
    }
}