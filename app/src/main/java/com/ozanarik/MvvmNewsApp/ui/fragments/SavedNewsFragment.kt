package com.ozanarik.MvvmNewsApp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.ozanarik.MvvmNewsApp.R
import com.ozanarik.MvvmNewsApp.databinding.FragmentSavedNewsBinding
import com.ozanarik.MvvmNewsApp.ui.adapter.NewsAdapter
import com.ozanarik.MvvmNewsApp.ui.viewmodels.NewsByCategoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedNewsFragment : Fragment() {
    private lateinit var binding:FragmentSavedNewsBinding
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var viewModel: NewsByCategoryViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSavedNewsBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(this)[NewsByCategoryViewModel::class.java]


        setupSavedNewsRv()
        handleSavedNews()
        return binding.root
    }



    private fun handleSavedNews(){

            viewModel.getAllSavedNews().observe(viewLifecycleOwner, Observer {articleList->

                articleList?.let { newsAdapter.differList.submitList(it) }

            })
        handleNavigationToArticleDetail()
        handleArticleDelete()

    }

    private fun handleNavigationToArticleDetail(){

        newsAdapter.setOnClickListener {

            val bundle = Bundle().apply {
                putSerializable("article",it)
            }

            findNavController().navigate(R.id.action_savedNewsFragment_to_articleDetailFragment,bundle)

        }

    }

    private fun setupSavedNewsRv(){

        newsAdapter = NewsAdapter()

        binding.savedNewsRv.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
        }
    }


    private fun handleArticleDelete(){

        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
                    ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                    ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        )
        {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                val position = viewHolder.adapterPosition
                val currentArticle = newsAdapter.differList.currentList[position]

                viewModel.deleteArticle(currentArticle)
                view?.let {
                    Snackbar.make(it,"Article successfully deleted!",Snackbar.LENGTH_LONG).apply {
                        setAction("UNDO"){
                            viewModel.insertArticleToLocalDB(currentArticle)
                        }.show()
                    }
                }
            }
        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.savedNewsRv)
    }

}