package com.batchlabs.android.batchstore.UI.Articles

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.batchlabs.android.batchstore.CartManager
import com.batchlabs.android.batchstore.UI.Data.Models.Article
import com.batchlabs.android.batchstore.core.R
import kotlinx.android.synthetic.main.activity_article_details.*
import kotlinx.android.synthetic.main.content_article_details.*

class ArticleDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_details)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val article = intent.getSerializableExtra("article") as Article
        if (article == null) {
            finish()
        }

        photoDetailsImageView.setImageResource(article.image)
        titleDetailsTextView.text = article.name
        priceDetailsTextView.text = "${article.price} €"

        addToCardButton.setOnClickListener{
            Toast.makeText(applicationContext,"Added \"${article.name}\" to the cart.",Toast.LENGTH_SHORT).show()
            CartManager.add(article)
            addToCardButton.text = "Add one more to cart"
        }
    }
}
