package com.ozanarik.MvvmNewsApp.ui.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ozanarik.MvvmNewsApp.R
import com.ozanarik.MvvmNewsApp.databinding.ItemArticleListBinding
import com.ozanarik.MvvmNewsApp.model.Article
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class NewsAdapter:RecyclerView.Adapter<NewsAdapter.NewsHolder>() {


    inner class NewsHolder(val binding:ItemArticleListBinding):RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<Article>(){


        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem==newItem
        }
    }

    val differList = AsyncListDiffer(this,diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding:ItemArticleListBinding = ItemArticleListBinding.inflate(layoutInflater,parent,false)

        return NewsHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: NewsHolder, position: Int) {

        val currentArticle = differList.currentList[position]
        holder.apply {

            binding.textViewDescription.text=currentArticle.description

            val newPublishDate = currentArticle.publishedAt
            var offsetDateTime = OffsetDateTime.parse(newPublishDate)

            var dateTimeFormat = DateTimeFormatter.ofPattern("dd MMM, uuuu")


            binding.textViewPublishedAt.text= dateTimeFormat.format(offsetDateTime)
            binding.textViewSource.text=currentArticle.source.name
            binding.textViewTitle.text=currentArticle.title
            Glide.with(holder.itemView)
                .load(currentArticle.urlToImage)
                .apply(RequestOptions().placeholder(R.drawable.placeholder))
                .into(binding.imageViewNew)


            holder.itemView.apply {

                setOnClickListener {
                    onItemClickListener?.let { it(currentArticle) }
                }
            }
        }
    }

    private var onItemClickListener : ((Article)->Unit)?=null

    fun setOnClickListener(listener:(Article)->Unit){

        onItemClickListener = listener

    }

    override fun getItemCount(): Int {
        return differList.currentList.size
    }
}