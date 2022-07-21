package com.testapp.mostpopularnews.ui.newsListing

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.testapp.mostpopularnews.R
import com.testapp.mostpopularnews.data.models.News
import com.testapp.mostpopularnews.databinding.ItemListContentBinding
import java.text.SimpleDateFormat
import java.util.*

class NewsAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<News, NewsAdapter.NewsViewHolder>(object :
        DiffUtil.ItemCallback<News>() {
        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem == newItem
        }
    }) {

    private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            ItemListContentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(current)
        }
        holder.bind(position)
    }

    inner class NewsViewHolder(private val itemListContentBinding: ItemListContentBinding) :
        RecyclerView.ViewHolder(itemListContentBinding.root) {
        init {
            itemView.setOnClickListener {
//                onItemClickListener(bindingAdapterPosition, getItem(bindingAdapterPosition))
            }
        }

        fun bind(position: Int) {
            val news = getItem(position)
            if (news.images!!.isNotEmpty()) {
                Glide.with(itemView.context)
                    .load(news.images)////.load(news.images?.get(0)!!.url)
                    .circleCrop()
                    .placeholder(R.drawable.ic_news_icon)
                    .error(R.drawable.ic_news_icon)
                    .into(itemListContentBinding.imageViewFeature)
            }
            itemListContentBinding.textViewTitle.text = news.title
            itemListContentBinding.textViewAuthor.text = news.author
            itemListContentBinding.textViewDate.text = simpleDateFormat.format(news.publishDate)
        }
    }

    class OnClickListener(val clickListener: (news: News) -> Unit) {
        fun onClick(news: News) = clickListener(news)
    }
}