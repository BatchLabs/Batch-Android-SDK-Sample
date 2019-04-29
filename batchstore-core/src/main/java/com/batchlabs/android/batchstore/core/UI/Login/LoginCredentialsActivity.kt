package com.batchlabs.android.batchstore.UI.Login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import com.batchlabs.android.batchstore.UserManager
import com.batchlabs.android.batchstore.core.R
import com.batchlabs.android.batchstore.core.ShopActivity
import kotlinx.android.synthetic.main.activity_login_credentials.*
import kotlinx.android.synthetic.main.content_login_credentials.*

class LoginCredentialsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_credentials)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        signInButton.setOnClickListener {
            val email = emailEditText.text.toString()

            if (email != null && email.isNotEmpty()) {
                val userManager = UserManager(applicationContext)

                if (!userManager.isLoggedIn) {
                    //Login in
                    Log.d("Login","Logged in : ${email}")
                    userManager.login(email)
                } else {
                    // Invalid state, ignore
                }

                //Open shop
                val intent = Intent(this, ShopActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            } else {
                showError()
            }
        }
    }

    private fun showError() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Invalid Login")
        builder.setMessage("Please enter a valid email.")
        builder.setPositiveButton("Ok"){
            dialog, which ->
            dialog.dismiss()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}
