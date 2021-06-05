package com.vk59.kostylev.ui.main

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.snackbar.Snackbar
import com.vk59.kostylev.R
import com.vk59.kostylev.Status
import com.vk59.kostylev.model.ItemVideo


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var descriptionText: TextView
    private lateinit var getButton: Button
    private lateinit var actualGifView: ImageView
    private lateinit var loadingBar: ProgressBar
    private lateinit var root: ConstraintLayout
    private var gifLinks: ArrayList<String> = arrayListOf()
    private var data: List<ItemVideo>? = null

    private var page = 0
    private var gifNumber = 0
    private val MAIN = "MAIN"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.main_fragment, container, false)
        initFragment(view)
        setGetOnClick()
        setBackOnClick()
        return view
    }


    private fun initFragment(view: View) {
        descriptionText = view.findViewById(R.id.linkText)
        getButton = view.findViewById(R.id.getButton)
        actualGifView = view.findViewById(R.id.actualGifImage)
        loadingBar = view.findViewById(R.id.loadingBar)
        root = view.findViewById(R.id.main)
    }

    private fun setGetOnClick() {
        getButton.setOnClickListener {
            gifNumber++
            if (data != null && gifNumber >= data!!.size) {
                page++
                viewModel.getLatest(page)
                gifNumber = 0
            } else {
                setContentToImageView(requireContext())
            }
        }
    }


    private fun setBackOnClick() {
        TODO("Not yet implemented")
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
                Status.LOADING -> viewLoading()
                Status.SUCCESS -> viewSuccess(it.data!!)
                Status.ERROR -> viewError()
            }
        })
    }

    private fun viewError() {
        Snackbar.make(root, getString(R.string.text_error), Snackbar.LENGTH_LONG).show()
        loadingBar.visibility = View.GONE
        actualGifView.setImageResource(R.drawable.ic_baseline_error_24)
    }

    private fun viewSuccess(data: List<ItemVideo>) {
        this.data = data
        setContentToImageView(requireContext())
    }

    private fun setContentToImageView(context: Context) {
        loadingBar.visibility = View.VISIBLE
        val url = data!![gifNumber].gifURL!!
        gifLinks.add(url)

        Glide.with(context)
            .load(url)
            .error(R.drawable.ic_baseline_error_24)
            .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
            .listener(object : RequestListener<Drawable> {
                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    loadingBar.visibility = View.GONE
                    actualGifView.visibility = View.VISIBLE
                    actualGifView.setImageDrawable(resource)
                    return false
                }

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    Log.d(MAIN, "Error ", e)
                    viewError()
                    return false
                }
            })
            .into(actualGifView)
        descriptionText.text = data!![gifNumber].description
    }

    private fun viewLoading() {
        loadingBar.visibility = View.VISIBLE
    }

}