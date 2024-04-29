package com.example.thenewsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.thenewsapp.R
import com.example.thenewsapp.models.Article
import com.example.thenewsapp.models.Source

class NewsAdapter: RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    lateinit var articleImage: ImageView
    lateinit var articleSource: TextView
    lateinit var articleTitle: TextView
    lateinit var articleDescription: TextView
    lateinit var articleDateTime: TextView

    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemCLickListener: ((Article) -> Unit)? = null

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = differ.currentList[position]

        holder.itemView.apply {
            // Инициализация переменных внутри onBindViewHolder
            val articleImage = findViewById<ImageView>(R.id.articleImage)
            val articleSource = findViewById<TextView>(R.id.articleSource)
            val articleTitle = findViewById<TextView>(R.id.articleTitle)
            val articleDescription = findViewById<TextView>(R.id.articleDescription)
            val articleDateTime = findViewById<TextView>(R.id.articleDateTime)

            // Загрузка изображения с помощью Glide
            Glide.with(context).load(article.urlToImage).into(articleImage)

            // Установка текста для TextView
            articleSource.text = article.source?.name ?: "Unknown Source"
            articleTitle.text = article.title
            articleDescription.text = article.description
            articleDateTime.text = article.publishedAt

            // Обработчик нажатия на элемент списка
            setOnClickListener {
                onItemClickListener?.invoke(article)
            }
        }
    }

    // Объявление переменной onItemClickListener в качестве свойства класса
    private var onItemClickListener: ((Article) -> Unit)? = null

    // Метод для установки обработчика нажатия на элемент списка
    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }
}
