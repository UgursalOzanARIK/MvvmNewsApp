package com.ozanarik.MvvmNewsApp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.ozanarik.MvvmNewsApp.databinding.FragmentArticleDetailBinding
import com.ozanarik.MvvmNewsApp.ui.viewmodels.NewsByCategoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleDetailFragment : Fragment() {
    private lateinit var binding: FragmentArticleDetailBinding
    private lateinit var viewModel: NewsByCategoryViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentArticleDetailBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(this)[NewsByCategoryViewModel::class.java]
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            handleWebviewForArticleDetail()
        }


    private fun handleWebviewForArticleDetail(){

        val articleArgs:ArticleDetailFragmentArgs by navArgs()
        val article = articleArgs.article
        binding.articleDetailWebView.apply {
            webViewClient = WebViewClient()
            article.url?.let { loadUrl(it) }
        }
        binding.fabLikeArticle.setOnClickListener {

            viewModel.insertArticleToLocalDB(article)
        }
    }



}


