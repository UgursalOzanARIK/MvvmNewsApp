package com.ozanarik.MvvmNewsApp.dependencyinjection

import android.content.Context
import androidx.room.Room
import com.ozanarik.MvvmNewsApp.data.localdb.ArticleDao
import com.ozanarik.MvvmNewsApp.data.localdb.ArticleDatabase
import com.ozanarik.MvvmNewsApp.data.remote.NewsApi
import com.ozanarik.MvvmNewsApp.data.repository.NewsRepository
import com.ozanarik.MvvmNewsApp.utils.Constants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {


    @Provides
    @Singleton
    fun provideArticleDatabase(@ApplicationContext context: Context):ArticleDatabase{

        return Room.databaseBuilder(context,ArticleDatabase::class.java,"article_db.db")
            .fallbackToDestructiveMigration()
            .build()
    }
    @Provides
    @Singleton
    fun provideArticleDao(db:ArticleDatabase):ArticleDao{

        return db.getArticleDao()
    }

    @Provides
    @Singleton
    fun provideRepository(newsApi: NewsApi,db:ArticleDatabase):NewsRepository{
        return NewsRepository(newsApi,db)
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttp:OkHttpClient):Retrofit{

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttp)
            .build()
    }
    @Provides
    @Singleton
    fun provideNewsApi(retrofit: Retrofit):NewsApi{

        return retrofit.create(NewsApi::class.java)

    }

    @Provides
    @Singleton
    fun provideHttpClient():OkHttpClient{

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .retryOnConnectionFailure(true)
            .build()

    }



}