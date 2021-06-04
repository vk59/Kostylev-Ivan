package com.vk59.kostylev.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import com.vk59.kostylev.R
import com.vk59.kostylev.Status
import com.vk59.kostylev.model.ItemVideo
import java.lang.StringBuilder

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var refreshLayout: SwipeRefreshLayout
    private lateinit var linkText: TextView
    private lateinit var getButton: Button
    private var page = 0
    private val MAIN = "MAIN"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view =inflater.inflate(R.layout.main_fragment, container, false)
        refreshLayout = view.findViewById(R.id.refreshLayout)
        linkText = view.findViewById(R.id.linkText)
        getButton = view.findViewById(R.id.getButton)
        setGetOnClick()
        return view
    }

    private fun setGetOnClick() {
        getButton.setOnClickListener {
            page++
            viewModel.getLatest(page)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        observeGetPosts()
        viewModel.getLatest(page)
    }

    private fun observeGetPosts() {
        viewModel.simpleLiveData.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> viewOneLoading()
                Status.SUCCESS -> viewOneSuccess(it.data)
                Status.ERROR -> viewError()
            }
        })
    }

    private fun viewError() {
        Snackbar.make(refreshLayout, "Something went wrong", Snackbar.LENGTH_LONG)
    }

    private fun viewOneSuccess(data: List<ItemVideo>?) {
        refreshLayout.isRefreshing = false

        var str: String = ""
        for (i: Int in 0..4) {
            Log.d(MAIN, data!![i].description!!)
            str = str.plus(data!![i].description + " " + data!![i].author + "\n")
        }

        linkText.text = str
    }

    private fun viewOneLoading() {
        refreshLayout.isRefreshing = true
    }

}