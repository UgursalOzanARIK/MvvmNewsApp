package com.ozanarik.MvvmNewsApp.data.repository

import com.ozanarik.MvvmNewsApp.data.localdb.ArticleDatabase
import com.ozanarik.MvvmNewsApp.data.remote.NewsApi
import com.ozanarik.MvvmNewsApp.model.Article
import com.ozanarik.MvvmNewsApp.model.NewsResponse
import com.ozanarik.MvvmNewsApp.utils.ApiState
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class NewsRepository @Inject constructor (private val newsApi: NewsApi, private val db: ArticleDatabase) {

    //FOR REMOTE
    fun getBreakingNews(countryCode:String,category:String,pageNum:Int):Flow<ApiState<NewsResponse>> = flow {

        try {
            emit(ApiState.Loading())

            val apiResponse = newsApi.getBreakingNews(countryCode,category,pageNum)

            emit(ApiState.Success(apiResponse.body()!!))

        }catch (e:HttpException){
            emit(ApiState.Error(e.localizedMessage?:e.message.toString()))
        }catch (e:IOException){
            emit(ApiState.Error(e.localizedMessage?:e.message.toString()))
        }
    }

    suspend fun searchNews(searchQuery:String,pageNum: Int):Flow<ApiState<NewsResponse>> = flow {

        try {
            emit(ApiState.Loading())
            val apiResponse = newsApi.searchNews(searchQuery,pageNum)

            emit(ApiState.Success(apiResponse.body()!!))
        }catch (e:HttpException){

            emit(ApiState.Error(e.localizedMessage?:e.message().toString()))

        }catch (e:IOException){
            emit(ApiState.Error(e.localizedMessage?:e.message.toString()))
        }
    }


    // FOR LOCAL

    suspend fun insertArticle(article: Article) = db.getArticleDao().insertArticle(article)

    fun getAllArticles()=db.getArticleDao().getAllSavedNews()

    suspend fun deleteArticle(article: Article) = db.getArticleDao().deleteArticle(article)

    }
