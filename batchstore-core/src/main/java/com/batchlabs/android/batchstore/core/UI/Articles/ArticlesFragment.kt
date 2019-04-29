package com.batchlabs.android.batchstore.UI.Articles

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.batchlabs.android.batchstore.EventManager
import com.batchlabs.android.batchstore.UI.Data.ArticlesFakeDataSource
import com.batchlabs.android.batchstore.core.R
import kotlinx.android.synthetic.main.fragment_articles.view.*


class ArticlesFragment : androidx.fragment.app.Fragment() {
    companion object {
        fun newInstance(): ArticlesFragment {
            return ArticlesFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_articles,container,false)

        val articles = ArticlesFakeDataSource().articles()
        val adapter = ArticlesAdapter(context!!,articles)

        val gridView = view.grid_view
        gridView.adapter = adapter
        gridView.columnWidth = gridView.width / 2

        gridView.setOnItemClickListener { _, _, position, _ ->
            val articleSelected = articles[position]

            EventManager().trackArticleVisit(articleSelected)

            val intent = Intent(context, ArticleDetailsActivity::class.java)
            intent.putExtra("article", articleSelected)
            startActivity(intent)
        }

        return view
    }
}