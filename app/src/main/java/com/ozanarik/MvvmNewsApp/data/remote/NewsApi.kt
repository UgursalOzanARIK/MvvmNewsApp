package com.ozanarik.MvvmNewsApp.data.remote

import com.ozanarik.MvvmNewsApp.model.NewsResponse
import com.ozanarik.MvvmNewsApp.utils.Constants.Companion.API_KEY
import com.ozanarik.MvvmNewsApp.utils.Constants.Companion.COUNTRY_CODE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {


    @GET("/v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country")countryCode:String = COUNTRY_CODE,
        @Query("category")category:String,
        @Query("page")pageNum:Int=1,
        @Query("apiKey")apiKey:String = API_KEY
    ):Response<NewsResponse>

    @GET("/v2/everything")
    suspend fun searchNews(
        @Query("q")q:String,
        @Query("page")pageNum: Int,
        @Query("apiKey")apiKey: String = API_KEY
    ):Response<NewsResponse>

}