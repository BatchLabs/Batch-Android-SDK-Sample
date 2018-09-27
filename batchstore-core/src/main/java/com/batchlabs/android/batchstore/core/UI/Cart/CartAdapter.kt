package com.batchlabs.android.batchstore.core.UI.Cart

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.batchlabs.android.batchstore.UI.Data.Models.Article
import com.batchlabs.android.batchstore.core.R
import kotlinx.android.synthetic.main.row_cart.view.*

class CartAdapter(val articles: ArrayList<Article>): RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = articles[position]
        holder.label.text = article.name
        holder.price.text = "${article.price} €"
        holder.photo.setImageResource(article.image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.row_cart, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val label = itemView.labelCartTextView!!
        val price = itemView.priceCartTextView!!
        val photo = itemView.photoCartImageView!!
    }
}