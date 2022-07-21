package com.testapp.mostpopularnews.ui.newsDetail

import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.testapp.mostpopularnews.R
import com.testapp.mostpopularnews.data.models.News
import com.testapp.mostpopularnews.databinding.NewsDetailFragmentBinding
import com.testapp.mostpopularnews.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class NewsDetailFragment : Fragment() {

    private lateinit var binding: NewsDetailFragmentBinding
    private val viewModel: NewsDetailViewModel by viewModels()
    private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = NewsDetailFragmentBinding.inflate(inflater, container, false)

        binding.toolbar.setNavigationOnClickListener { findNavController().popBackStack() }


        val newsObjReceived = requireArguments().getSerializable("news_obj") as News

        displayNews(newsObjReceived)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        arguments?.getInt("id")?.let { viewModel.start(it) }
//        setupObservers()
    }

//    private fun setupObservers() {
//        viewModel.character.observe(viewLifecycleOwner, Observer {
//            when (it.status) {
//                Resource.Status.SUCCESS -> {
//                    bindCharacter(it.data!!)
//                    binding.progressBar.visibility = View.GONE
//                    binding.characterCl.visibility = View.VISIBLE
//                }
//
//                Resource.Status.ERROR ->
//                    Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
//
//                Resource.Status.LOADING -> {
//                    binding.progressBar.visibility = View.VISIBLE
//                    binding.characterCl.visibility = View.GONE
//                }
//            }
//        })
//    }

//    private fun bindCharacter(character: Character) {
//        binding.name.text = character.name
//        binding.species.text = character.species
//        binding.status.text = character.status
//        binding.gender.text = character.gender
//        Glide.with(binding.root)
//            .load(character.image)
//            .transform(CircleCrop())
//            .into(binding.image)
//    }

    private fun startLoadingProgress() {
        binding.linearProgressIndicator.visibility = View.VISIBLE
        binding.layoutFailureState.buttonFailureRetry.isEnabled = false
    }

    private fun stopLoadingProgress() {
        binding.linearProgressIndicator.visibility = View.GONE
        binding.layoutFailureState.buttonFailureRetry.isEnabled = true
    }


    private fun displayNews(news: News) {
//        println("news images url"+ news.images!![0].url)
//        val uri = Uri.parse(news.images!![0].url)
//        binding.newsImage.setImageURI(uri)
        Glide.with(requireContext())
            .load(news.images) ///.load(news.images?.get(0)!!.url)
            .placeholder(R.drawable.ic_news_icon)
            .error(R.drawable.ic_news_icon)
            .into(binding.newsImage)
        binding.apply {
            layoutEmptyState.root.visibility = View.GONE
            linearLayoutDetails.visibility = View.VISIBLE
            if (news.images!!.isEmpty()) {
                newsImage.visibility = View.GONE
                collapsingToolbarLayout.isTitleEnabled = true
            } else {
                newsImage.visibility = View.VISIBLE
                collapsingToolbarLayout.isTitleEnabled = false
            }

            toolbar.title = news.title
            textViewTitle.text = news.title
            textViewDate.text = simpleDateFormat.format(news.publishDate)
            textViewCategory.text = news.category
            textViewAbstract.text = news.newsAbstract
            textViewAuthor.text = news.author
            textViewSource.text = news.source

            webView.webViewClient = object : WebViewClient() {
                private var hasError = false
                private fun setHasError(errorDescription: String?) {
                    // Don't display errors for disabling of JavaScript
                    if (errorDescription == null || !errorDescription.contains("ERR_BLOCKED_BY_RESPONSE")) {
                        hasError = true
                        stopLoadingProgress()
                        layoutFailureState.textViewFailureMessage.text =
                            getString(R.string.error_loading_web)
                        layoutFailureState.root.visibility = View.VISIBLE
                    }
                }

                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    startLoadingProgress()
                    hasError = false
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    stopLoadingProgress()
                    layoutFailureState.root.visibility = if (hasError) View.VISIBLE else View.GONE
                }

                override fun onReceivedError(
                    view: WebView?,
                    errorCode: Int,
                    description: String?,
                    failingUrl: String?
                ) {
                    super.onReceivedError(view, errorCode, description, failingUrl)
                    setHasError(description.toString())
                }

                override fun onReceivedError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?
                ) {
                    super.onReceivedError(view, request, error)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        setHasError(error?.description.toString())
                    }
                }
            }
            webView.loadUrl(news.url)
            binding.layoutFailureState.buttonFailureRetry.setOnClickListener {
                webView.loadUrl(news.url)
            }
        }
    }
}
