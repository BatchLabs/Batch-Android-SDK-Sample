package com.batchlabs.android.batchstore.UI.Login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.batchlabs.android.batchstore.UserManager
import com.batchlabs.android.batchstore.core.R
import com.batchlabs.android.batchstore.core.ShopActivity
import kotlinx.android.synthetic.main.activity_login_landing.*
import kotlinx.android.synthetic.main.content_login_landing.*

class LoginLandingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_landing)
        setSupportActionBar(toolbar)

        UserManager(applicationContext).onboardingAttempted = true

        signInButton.setOnClickListener {
            val intent = Intent(this,LoginCredentialsActivity::class.java)
            startActivity(intent)
        }

        notNowButton.setOnClickListener {
            val intent = Intent(this, ShopActivity::class.java)
            startActivity(intent)
        }
    }

}
