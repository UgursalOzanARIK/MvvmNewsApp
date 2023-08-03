package com.ozanarik.MvvmNewsApp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ozanarik.MvvmNewsApp.data.repository.NewsRepository
import com.ozanarik.MvvmNewsApp.model.Article
import com.ozanarik.MvvmNewsApp.model.NewsResponse
import com.ozanarik.MvvmNewsApp.utils.ApiState
import com.ozanarik.MvvmNewsApp.utils.Constants.Companion.CATEGORY_BUSINESS
import com.ozanarik.MvvmNewsApp.utils.Constants.Companion.CATEGORY_ENTERTAINMENT
import com.ozanarik.MvvmNewsApp.utils.Constants.Companion.CATEGORY_GENERAL
import com.ozanarik.MvvmNewsApp.utils.Constants.Companion.CATEGORY_HEALTH
import com.ozanarik.MvvmNewsApp.utils.Constants.Companion.CATEGORY_SCIENCE
import com.ozanarik.MvvmNewsApp.utils.Constants.Companion.CATEGORY_SPORTS
import com.ozanarik.MvvmNewsApp.utils.Constants.Companion.CATEGORY_TECHNOLOGY
import com.ozanarik.MvvmNewsApp.utils.Constants.Companion.COUNTRY_CODE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsByCategoryViewModel @Inject constructor(private val newsRepository: NewsRepository):ViewModel() {

    private val _breakingNewsByCategoryState:MutableStateFlow<ApiState<NewsResponse>> = MutableStateFlow(ApiState.Loading())
    val breakingNewsByCategory:StateFlow<ApiState<NewsResponse>> = _breakingNewsByCategoryState

    private val _searchNewsState:MutableStateFlow<ApiState<NewsResponse>> = MutableStateFlow(ApiState.Loading())
    val searchNewsState:StateFlow<ApiState<NewsResponse>> = _searchNewsState

    init {
        fetchBreakingNews(0)
    }

    fun fetchBreakingNews(position: Int) = viewModelScope.launch {

        handleBreakingNewsResponse(position)
    }


    //FETCH NEWS AFTER CONTROLLING THEIR STATES
    private fun handleBreakingNewsResponse(position:Int)=viewModelScope.launch {

        val category = when(position){

            0->     CATEGORY_GENERAL
            1->     CATEGORY_BUSINESS
            2->     CATEGORY_ENTERTAINMENT
            3->     CATEGORY_HEALTH
            4->     CATEGORY_SCIENCE
            5->     CATEGORY_SPORTS
            6->     CATEGORY_TECHNOLOGY
            else->  CATEGORY_GENERAL
        }
        newsRepository.getBreakingNews(COUNTRY_CODE,category,1).collect{resultResponse->

            when(resultResponse) {
                is ApiState.Success -> {
                    _breakingNewsByCategoryState.value = resultResponse.data?.let { ApiState.Success(it) }!!
                }
                is ApiState.Error ->
                {
                    _breakingNewsByCategoryState.value = resultResponse.message?.let { ApiState.Error(it) }!!
                }
                is ApiState.Loading->
                {
                    _breakingNewsByCategoryState.value = ApiState.Loading()
                }
            }
            }
        }

    fun handleSearchNews(searchQuery: String,pageNum: Int)=viewModelScope.launch {

        searchNews(searchQuery,pageNum)
    }

    private fun searchNews(searchQuery:String,pageNum:Int)=viewModelScope.launch {

        newsRepository.searchNews(searchQuery,pageNum).collect{resultResponse->

            when(resultResponse){
                is ApiState.Success->{
                    _searchNewsState.value = resultResponse.data?.let { ApiState.Success(it) }!!
                }
                is ApiState.Error->{
                    _searchNewsState.value = resultResponse.message?.let { ApiState.Error(it) }!!
                }
                is ApiState.Loading->{
                    _searchNewsState.value = ApiState.Loading()
                }
            }
        }
    }

    fun insertArticleToLocalDB(article: Article) = viewModelScope.launch {newsRepository.insertArticle(article = article)}

    fun deleteArticle(article: Article) = viewModelScope.launch { newsRepository.deleteArticle(article) }

    fun getAllSavedNews()=newsRepository.getAllArticles().asLiveData()


}
