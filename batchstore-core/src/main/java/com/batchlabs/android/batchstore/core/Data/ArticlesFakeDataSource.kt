package com.batchlabs.android.batchstore.UI.Data

import com.batchlabs.android.batchstore.UI.Data.Models.Article
import com.batchlabs.android.batchstore.core.R

class ArticlesFakeDataSource(){

    val articles:ArrayList<Article> = ArrayList<Article>()

    fun articles(): ArrayList<Article> {
        generateFakeArticles()
        return articles
    }

    private fun generateFakeArticles(){
        articles.add(Article("Mocassins",280, R.drawable.mocassins))
        articles.add(Article("Aviators",140, R.drawable.aviators))
        articles.add(Article("Sac a main noir",300, R.drawable.sac_noir))
        articles.add(Article("Chaussures à talon",400, R.drawable.chaussure_talon))
        articles.add(Article("Collier",98, R.drawable.collier))
        articles.add(Article("Écharpe",110, R.drawable.echarpe))
        articles.add(Article("Patek Philippe",110, R.drawable.patek_philippe))
        articles.add(Article("Blouse",260, R.drawable.blouse))
        articles.add(Article("Manteau",420, R.drawable.manteau))
        articles.add(Article("Submariner",420, R.drawable.submariner))
        articles.add(Article("Baskets",110, R.drawable.basket))
        articles.add(Article("Casquette",110, R.drawable.casquette))
    }
}