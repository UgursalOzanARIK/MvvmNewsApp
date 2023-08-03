package com.ozanarik.MvvmNewsApp.ui.fragments.newcategoriesfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ozanarik.MvvmNewsApp.databinding.FragmentBusinessBinding
import com.ozanarik.MvvmNewsApp.ui.adapter.NewsAdapter

class BusinessFragment : Fragment() {
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var binding: FragmentBusinessBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBusinessBinding.inflate(inflater,container,false)
        newsAdapter = NewsAdapter()
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)


    }









}