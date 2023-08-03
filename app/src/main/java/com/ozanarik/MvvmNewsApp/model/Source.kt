package com.ozanarik.MvvmNewsApp.model


import com.google.gson.annotations.Expose

data class Source(
    @Expose
    val id: String? = null, // engadget
    @Expose
    val name: String = "" // Engadget
)