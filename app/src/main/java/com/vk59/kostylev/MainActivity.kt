package com.vk59.kostylev

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vk59.kostylev.databinding.MainActivityBinding
import com.vk59.kostylev.ui.hot.HotFragment
import com.vk59.kostylev.ui.main.MainFragment
import com.vk59.kostylev.ui.top.TopFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction()
//                    .replace(R.id.container, MainFragment.newInstance())
//                    .commitNow()
//        }

        setupViewPager()

        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }

    private fun setupViewPager() {
        val adapter = ViewPagerAdapter(supportFragmentManager, binding.tabLayout.tabCount)

        adapter.addFragment(MainFragment(), getString(R.string.latestTab))
        adapter.addFragment(HotFragment(), getString(R.string.hotTab))
        adapter.addFragment(TopFragment(), getString(R.string.topTab))
        binding.viewPager.adapter = adapter
        binding.viewPager.offscreenPageLimit = 2
    }
}