package com.ozanarik.MvvmNewsApp.ui.fragments.newcategoriesfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ozanarik.MvvmNewsApp.R
import com.ozanarik.MvvmNewsApp.data.remote.NewsApi
import com.ozanarik.MvvmNewsApp.databinding.FragmentGeneralBinding
import com.ozanarik.MvvmNewsApp.ui.adapter.NewsAdapter
import com.ozanarik.MvvmNewsApp.ui.viewmodels.NewsByCategoryViewModel
import javax.inject.Inject

class GeneralFragment : Fragment() {
    @Inject lateinit var newsApi: NewsApi
    private lateinit var newsAdapter:NewsAdapter
    private lateinit var binding: FragmentGeneralBinding
    private lateinit var viewModel:NewsByCategoryViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding= FragmentGeneralBinding.inflate(inflater,container,false)



        return inflater.inflate(R.layout.fragment_general, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }








}