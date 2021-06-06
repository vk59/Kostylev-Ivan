package com.vk59.kostylev.ui.top

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.vk59.kostylev.databinding.MainFragmentBinding
import com.vk59.kostylev.model.ItemVideo

class TopFragment : Fragment() {

    companion object {
        fun newInstance() = TopFragment()
    }

    private lateinit var viewModel: TopViewModel
    private var gifLinks: ArrayList<ItemVideo> = arrayListOf()
    private var data: List<ItemVideo>? = null
    private lateinit var binding: MainFragmentBinding

    private var page = 0
    private var gifNumber = 0
    private var globalGifNumber = 0
    private val TOP = "TOP"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.main_fragment, container, false)
        binding = MainFragmentBinding.bind(view)
        setGetOnClick()
        setBackOnClick()
        return binding.root
    }


    private fun setGetOnClick() {
        binding.nextGifButton.setOnClickListener {
            binding.previousButton.isEnabled = true
            globalGifNumber++
            if (globalGifNumber < gifLinks.size) {
                setPreviousContent(requireContext())
            } else {
                gifNumber++
                if (data != null && gifNumber >= data!!.size) {
                    page++
                    viewModel.getTop(page)
                    gifNumber = 0
                } else {
                    setNewContent(requireContext())
                }
            }
        }
    }


    private fun setBackOnClick() {
        binding.previousButton.setOnClickListener {
            globalGifNumber--
            if (globalGifNumber == 0) {
                binding.previousButton.isEnabled = false
            }
            setPreviousContent(requireContext())
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TopViewModel::class.java)

        observeGetPosts()
        viewModel.getTop(page)
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
        Snackbar.make(binding.root, getString(R.string.text_error), Snackbar.LENGTH_LONG).show()
        binding.loadingBar.visibility = View.GONE
        binding.actualGifImage.setImageResource(R.drawable.ic_baseline_error_24)
    }

    private fun viewSuccess(data: List<ItemVideo>) {
        this.data = data
        setNewContent(requireContext())
    }

    private fun setNewContent(context: Context) {
        binding.loadingBar.visibility = View.VISIBLE
        gifLinks.add(data!![gifNumber])
        setContentToView(context, data!![gifNumber].gifURL!!, data!![gifNumber].description!!)
    }

    private fun setContentToView(context: Context, url: String, description: String) {
        Glide.with(context)
            .load(url)
            .error(R.drawable.ic_baseline_error_24)
            .listener(object : RequestListener<Drawable> {
                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.loadingBar.visibility = View.GONE
                    binding.actualGifImage.visibility = View.VISIBLE
                    binding.actualGifImage.setImageDrawable(resource)
                    return false
                }

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    Log.d(TOP, "Error ", e)
                    viewError()
                    return false
                }
            })
            .into(binding.actualGifImage)
        binding.descriptionText.text = description
    }

    private fun setPreviousContent(context: Context) {
        setContentToView(
            context, gifLinks[globalGifNumber].gifURL!!,
            gifLinks[globalGifNumber].description!!
        )
    }

    private fun viewLoading() {
        binding.loadingBar.visibility = View.VISIBLE
    }

}