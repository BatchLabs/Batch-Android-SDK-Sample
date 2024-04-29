package com.batchlabs.android.batchstore

import com.batchlabs.android.batchstore.ui.Data.Models.Article
import com.batch.android.Batch
import com.batch.android.BatchEventAttributes

class EventManager {
    fun trackArticleVisit(article: Article){
        Batch.Profile.trackEvent("ARTICLE_VIEW", BatchEventAttributes().apply {
            put(BatchEventAttributes.LABEL_KEY, article.name)
            put("name", article.name)
        })
    }

    fun trackAddArticleToCart(article: Article){
        Batch.Profile.trackEvent("ADD_TO_CART", BatchEventAttributes().apply {
            put(BatchEventAttributes.LABEL_KEY, article.name)
            put("name", article.name)
        })
    }

    fun trackCheckout(amount: Double){
        Batch.Profile.trackEvent("CHECKOUT", BatchEventAttributes().apply {
            put("amount", amount)
        })
    }
}
