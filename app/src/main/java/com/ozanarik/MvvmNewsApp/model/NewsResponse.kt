package com.ozanarik.MvvmNewsApp.model


import com.google.gson.annotations.Expose

data class NewsResponse(
    @Expose
    val articles: List<Article> = listOf(),
    @Expose
    val status: String = "", // ok
    @Expose
    val totalResults: Int = 0 // 36
)