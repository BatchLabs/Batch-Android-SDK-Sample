package com.batchlabs.android.batchstore

import com.batchlabs.android.batchstore.ui.Data.Models.Article
import com.batch.android.Batch

class EventManager {
    init {}

    fun trackArticleVisit(article: Article){
        Batch.User.trackEvent("ARTICLE_VIEW", article.name)
    }

    fun trackAddArticleToCart(article: Article){
        Batch.User.trackEvent("ADD_TO_CART", article.name)
    }

    fun trackCheckout(amount: Double){
        Batch.User.trackEvent("CHECKOUT")
        Batch.User.trackTransaction(amount)
    }
}