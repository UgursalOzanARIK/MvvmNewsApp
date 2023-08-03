package com.ozanarik.MvvmNewsApp.utils

class Constants {

    companion object{

        const val BASE_URL = "https://newsapi.org"
        const val API_KEY = "0d1580a793a84593babaabd8fb4cf8bc"
        const val COUNTRY_CODE = "us"

        val NEWS_CATEGORY_LIST = listOf<String>("General","Business","Entertainment","Health","Science","Sports","Technology")

        const val CATEGORY_GENERAL = "general"
        const val CATEGORY_BUSINESS = "business"
        const val CATEGORY_ENTERTAINMENT = "entertainment"
        const val CATEGORY_HEALTH = "health"
        const val CATEGORY_SCIENCE = "science"
        const val CATEGORY_SPORTS = "sports"
        const val CATEGORY_TECHNOLOGY = "technology"

    }

}