package com.batchlabs.android.batchstore

import com.batchlabs.android.batchstore.ui.Data.Models.Article

class CartManager() {
    companion object Factory {
        val articles: ArrayList<Article> = ArrayList()

        fun add(article: Article){
            articles.add(article)
            EventManager().trackAddArticleToCart(article)
        }

        fun clear(){
            articles.clear()
        }

        fun checkout(){
            EventManager().trackCheckout(computeTotal().toDouble())
            clear()
        }

        fun computeTotal(): Int {
            return articles.map { a -> a.price }.reduce{total,next -> total + next}
        }
    }
}
