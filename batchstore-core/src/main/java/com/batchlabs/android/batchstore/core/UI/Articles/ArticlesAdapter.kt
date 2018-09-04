package com.batchlabs.android.batchstore.UI.Articles

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.batchlabs.android.batchstore.UI.Data.Models.Article
import com.batchlabs.android.batchstore.core.R
import kotlinx.android.synthetic.main.row_article.view.*

class ArticlesAdapter : BaseAdapter {
    var articlesList = ArrayList<Article>()
    var context: Context? = null

    constructor(context: Context, articlesList: ArrayList<Article>) : super() {
        this.context = context
        this.articlesList = articlesList
    }

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

        var inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var articleView = inflator.inflate(R.layout.row_article, null)
        articleView.labelTextView.text = article.name
        articleView.priceTextView.text = "${article.price} €"
        articleView.photoImageView.setImageResource(article.image)

        return articleView
    }
}