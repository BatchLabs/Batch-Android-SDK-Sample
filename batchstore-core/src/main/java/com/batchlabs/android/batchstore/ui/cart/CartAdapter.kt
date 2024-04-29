package com.batchlabs.android.batchstore.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.batchlabs.android.batchstore.ui.Data.Models.Article
import com.batchlabs.android.batchstore.core.databinding.RowCartBinding

class CartAdapter(val articles: ArrayList<Article>): RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = articles[position]
        holder.label.text = article.name
        holder.price.text = "${article.price} €"
        holder.photo.setImageResource(article.image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = RowCartBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    class ViewHolder(itemView: RowCartBinding): RecyclerView.ViewHolder(itemView.root){
        val label = itemView.labelCartTextView
        val price = itemView.priceCartTextView
        val photo = itemView.photoCartImageView
    }
}
