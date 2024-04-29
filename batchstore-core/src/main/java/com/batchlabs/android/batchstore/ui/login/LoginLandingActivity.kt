package com.batchlabs.android.batchstore.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.batchlabs.android.batchstore.UserManager
import com.batchlabs.android.batchstore.ShopActivity
import com.batchlabs.android.batchstore.core.databinding.ActivityLoginLandingBinding

class LoginLandingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginLandingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginLandingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        UserManager(applicationContext).onboardingAttempted = true

        binding.content.signInButton.setOnClickListener {
            val intent = Intent(this, LoginCredentialsActivity::class.java)
            startActivity(intent)
        }

        binding.content.notNowButton.setOnClickListener {
            val intent = Intent(this, ShopActivity::class.java)
            startActivity(intent)
        }
    }

}
