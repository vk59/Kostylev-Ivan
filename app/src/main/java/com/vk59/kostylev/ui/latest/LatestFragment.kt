package com.vk59.kostylev.ui.latest

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.vk59.kostylev.Status
import com.vk59.kostylev.ui.BaseFragment


class LatestFragment : BaseFragment() {
    private lateinit var viewModel: LatestViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(LatestViewModel::class.java)
        super.onActivityCreated(savedInstanceState)
    }

    override fun getRequest() {
        viewModel.getLatest(page)
    }


    override fun observeGetPosts() {
        viewModel.simpleLiveData.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> viewLoading()
                Status.SUCCESS -> {
                    if (!it.data.isNullOrEmpty()) {
                        viewSuccess(it.data)
                    } else {
                        viewEmpty()
                    }
                }
                Status.ERROR -> viewError()
            }
        })
    }


}