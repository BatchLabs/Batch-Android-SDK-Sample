package com.batchlabs.android.batchstore.ui.articles

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import com.batchlabs.android.batchstore.BaseActivity
import com.batchlabs.android.batchstore.CartManager
import com.batchlabs.android.batchstore.ui.Data.Models.Article
import com.batchlabs.android.batchstore.core.databinding.ActivityArticleDetailsBinding
import com.batchlabs.android.batchstore.core.databinding.ContentArticleDetailsBinding

class ArticleDetailsActivity : BaseActivity() {

    private lateinit var binding: ActivityArticleDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val article = intent.getSerializableExtra("article") as Article
        if (article == null) {
            finish()
        }

        binding.content.photoDetailsImageView.setImageResource(article.image)
        binding.content.titleDetailsTextView.text = article.name
        binding.content.priceDetailsTextView.text = "${article.price} €"

        binding.content.addToCardButton.setOnClickListener{
            Toast.makeText(applicationContext,"Added \"${article.name}\" to the cart.",Toast.LENGTH_SHORT).show()
            CartManager.add(article)
            binding.content.addToCardButton.text = "Add one more to cart"
        }
    }
}
