package com.ozanarik.MvvmNewsApp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.ozanarik.MvvmNewsApp.R
import com.ozanarik.MvvmNewsApp.databinding.FragmentBreakingNewsBinding
import com.ozanarik.MvvmNewsApp.ui.adapter.NewsAdapter
import com.ozanarik.MvvmNewsApp.ui.adapter.ViewPagerAdapter
import com.ozanarik.MvvmNewsApp.ui.viewmodels.NewsByCategoryViewModel
import com.ozanarik.MvvmNewsApp.utils.ApiState
import com.ozanarik.MvvmNewsApp.utils.Constants.Companion.NEWS_CATEGORY_LIST
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class BreakingNewsFragment : Fragment(),SearchView.OnQueryTextListener {
    private lateinit var viewModel:NewsByCategoryViewModel
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var binding: FragmentBreakingNewsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBreakingNewsBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment

        (activity as AppCompatActivity).setSupportActionBar(binding.myToolbar)

        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.toolbar_menu,menu)
                val searchItem = menu.findItem(R.id.action_Search)
                val searchView = searchItem.actionView as SearchView
                searchView.setOnQueryTextListener(this@BreakingNewsFragment)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return false
            }
        },viewLifecycleOwner,Lifecycle.State.RESUMED)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[NewsByCategoryViewModel::class.java]
        handleTabLayout()

        handleNews(0)

    }

    private fun handleTabLayout(){
        val newsCategoryList = NEWS_CATEGORY_LIST
        val newsTabLayout = binding.newsTabLayout
        val newsViewPager = binding.newsViewPager

        newsViewPager.adapter = ViewPagerAdapter(requireActivity().supportFragmentManager,requireActivity().lifecycle)

        //CREATE DIFFERENT TABS WITH DIFFERENT NAMES
        TabLayoutMediator(newsTabLayout,newsViewPager){tab,position->
            tab.text = newsCategoryList[position]
        }.attach()

        newsTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {

                    tab?.position?.let { handleNews(it) }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {


            }
        })
    }

    private fun handleSearchNews(searchQuery:String){

        viewModel.handleSearchNews(searchQuery,1)
       viewLifecycleOwner.lifecycleScope.launch {
            viewModel.searchNewsState.collect{resultResponse->

                when(resultResponse){

                    is ApiState.Success->{
                        resultResponse.data?.let { newsAdapter.differList.submitList(it.articles) }
                    }
                    is ApiState.Error->{
                        Snackbar.make(requireView(),"An error occured trying to fetch the news...",Snackbar.LENGTH_LONG).show()
                    }
                    is ApiState.Loading->{
                        Snackbar.make(requireView(),"Fetching the news...",Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun handleNews(position:Int){

            viewModel.fetchBreakingNews(position)
        viewLifecycleOwner.lifecycleScope.launch {

                viewModel.breakingNewsByCategory.collect{resultResponse->

                    when(resultResponse){

                        is ApiState.Success->{

                            resultResponse.data?.let { newsAdapter.differList.submitList(it.articles) }
                        }
                        is ApiState.Loading->{
                            Snackbar.make(requireView(),"Fetching News...",Snackbar.LENGTH_LONG).show()
                        }
                        is ApiState.Error->{
                            Snackbar.make(requireView(),resultResponse.message!!, Snackbar.LENGTH_LONG).show()
                        }
                    }
                }
            }
        setupRecyclerView()
        handleNavigationToArticleDetail()
    }

    private fun setupRecyclerView(){
        newsAdapter = NewsAdapter()
        binding.recyclerViewNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun handleNavigationToArticleDetail(){
        newsAdapter.setOnClickListener {

            val bundle = Bundle().apply {
                putSerializable("article",it)
            }
            findNavController().navigate(R.id.action_breakingNewsFragment_to_articleDetailFragment,bundle)
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let { handleSearchNews(it) }
        handleNavigationToArticleDetail()
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

}