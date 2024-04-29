package com.batchlabs.android.batchstore.ui.articles

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.batchlabs.android.batchstore.ui.Data.Models.Article
import com.batchlabs.android.batchstore.core.databinding.RowArticleBinding

class ArticlesAdapter(context: Context, private var articlesList: ArrayList<Article>) : BaseAdapter() {
    var context: Context? = context

    override fun getCount(): Int {
        return articlesList.size
    }

    override fun getItem(position: Int): Any {
        return articlesList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val article = this.articlesList[position]

        val layoutInflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val articleView = RowArticleBinding.inflate(layoutInflater)
        articleView.labelTextView.text = article.name
        articleView.priceTextView.text = "${article.price} €"
        articleView.photoImageView.setImageResource(article.image)

        return articleView.root
    }
}
