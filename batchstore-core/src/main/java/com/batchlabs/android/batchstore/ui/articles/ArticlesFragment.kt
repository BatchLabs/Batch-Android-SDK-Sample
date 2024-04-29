package com.batchlabs.android.batchstore.ui.articles

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.batchlabs.android.batchstore.EventManager
import com.batchlabs.android.batchstore.ui.Data.ArticlesFakeDataSource
import com.batchlabs.android.batchstore.core.databinding.FragmentArticlesBinding


class ArticlesFragment : androidx.fragment.app.Fragment() {
    companion object {
        fun newInstance(): ArticlesFragment {
            return ArticlesFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  FragmentArticlesBinding.inflate(inflater)

        val articles = ArticlesFakeDataSource().articles()
        val adapter = ArticlesAdapter(requireContext(),articles)

        val gridView = view.gridView
        gridView.adapter = adapter
        gridView.columnWidth = gridView.width / 2

        gridView.setOnItemClickListener { _, _, position, _ ->
            val articleSelected = articles[position]

            EventManager().trackArticleVisit(articleSelected)

            val intent = Intent(context, ArticleDetailsActivity::class.java)
            intent.putExtra("article", articleSelected)
            startActivity(intent)
        }

        return view.root
    }
}
