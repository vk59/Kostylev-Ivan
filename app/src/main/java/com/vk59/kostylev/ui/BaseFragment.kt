package com.vk59.kostylev.ui

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.snackbar.Snackbar
import com.vk59.kostylev.R
import com.vk59.kostylev.databinding.MainFragmentBinding
import com.vk59.kostylev.model.ItemVideo


abstract class BaseFragment : Fragment() {

    private var gifLinks: ArrayList<ItemVideo> = arrayListOf()
    protected var data: List<ItemVideo>? = null
    protected lateinit var binding: MainFragmentBinding

    private val STATE_OK = 0
    private val STATE_NOT_FOUND = 1
    private val STATE_ERROR = 2
    private val STATE_LOADING = 3

    private var visibilityState: Int = 0

    protected var page = 0
    private var gifNumber = 0
    private var globalGifNumber = 0
    protected val MAIN = "MAIN"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.main_fragment, container, false)
        binding = MainFragmentBinding.bind(view)
        setGetOnClick()
        setBackOnClick()
        setTryAgainOnClick()
        return binding.root
    }

    private fun setTryAgainOnClick() {
        binding.tryAgainButton.setOnClickListener {
            viewLoading()
            getRequest()
        }
    }


    private fun setGetOnClick() {
        binding.nextGifButton.setOnClickListener {
            setNewVisibility(STATE_LOADING)
            binding.previousButton.isEnabled = true
            globalGifNumber++
            if (globalGifNumber < gifLinks.size) {
                setPreviousContent(requireContext())
            } else {
                gifNumber++
                if (data != null && gifNumber >= data!!.size) {
                    page++
                    gifNumber = 0
                    getRequest()
                } else if (data != null && data!!.isNotEmpty()) {
                    setNewContent(requireContext())
                }
            }
        }
    }

    abstract fun getRequest()

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
        observeGetPosts()
        getRequest()
    }

    abstract fun observeGetPosts()

    protected fun viewError() {
        setNewVisibility(STATE_ERROR)
        Snackbar.make(binding.root, getString(R.string.text_error), Snackbar.LENGTH_LONG).show()
    }

    protected fun viewSuccess(data: List<ItemVideo>) {
        setNewVisibility(STATE_OK)
        this.data = data
        setNewContent(requireContext())
    }

    protected fun viewLoading() {
        setNewVisibility(STATE_LOADING)
    }

    protected fun viewEmpty() {
        setNewVisibility(STATE_NOT_FOUND)
    }

    private fun setNewContent(context: Context) {
        setNewVisibility(STATE_LOADING)
        gifLinks.add(data!![gifNumber])
        setContentToView(context, data!![gifNumber].gifURL!!, data!![gifNumber].description!!)
    }

    private fun setContentToView(context: Context, url: String, description: String) {
        Glide.with(context)
            .load(url)
            .listener(object : RequestListener<Drawable> {
                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    setNewVisibility(STATE_OK)
                    binding.actualGifImage.setImageDrawable(resource)
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
            .into(binding.actualGifImage)
        binding.descriptionText.text = description
    }

    private fun setPreviousContent(context: Context) {
        setContentToView(
            context, gifLinks[globalGifNumber].gifURL!!,
            gifLinks[globalGifNumber].description!!
        )
    }

    private fun setNewVisibility(newState: Int) {
        visibilityState = newState
        when (visibilityState) {
            STATE_OK -> {
                binding.errorIndicateLayout.visibility = View.GONE
                binding.tryAgainButton.visibility = View.GONE
                binding.nextGifButton.visibility = View.VISIBLE
                binding.actualGifImage.visibility = View.VISIBLE
                binding.previousButton.visibility = View.VISIBLE
                binding.descriptionText.visibility = View.VISIBLE
            }
            STATE_ERROR -> {
                binding.loadingBar.visibility = View.GONE
                binding.nextGifButton.visibility = View.GONE
                binding.previousButton.visibility = View.GONE
                binding.actualGifImage.visibility = View.GONE
                binding.descriptionText.visibility = View.GONE
                binding.tryAgainButton.visibility = View.VISIBLE
                binding.errorIndicateLayout.visibility = View.VISIBLE
                binding.errorInfo.text = getString(R.string.connectionError)
                binding.errorIcon.setImageResource(R.drawable.icon_no_connection)
            }
            STATE_NOT_FOUND -> {
                binding.loadingBar.visibility = View.GONE
                binding.nextGifButton.visibility = View.GONE
                binding.previousButton.visibility = View.GONE
                binding.descriptionText.visibility = View.GONE
                binding.actualGifImage.visibility = View.GONE
                binding.tryAgainButton.visibility = View.VISIBLE
                binding.errorIndicateLayout.visibility = View.VISIBLE
                binding.errorInfo.text = getString(R.string.noGifsExplanation)
                binding.errorIcon.setImageResource(R.drawable.icon_not_found)
            }
            else -> {
                binding.errorIndicateLayout.visibility = View.GONE
                binding.loadingBar.visibility = View.VISIBLE
            }
        }
    }

}