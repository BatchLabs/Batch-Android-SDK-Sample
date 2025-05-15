package com.batchlabs.android.batchstore.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.batchlabs.android.batchstore.BaseActivity
import com.batchlabs.android.batchstore.ShopActivity
import com.batchlabs.android.batchstore.UserManager
import com.batchlabs.android.batchstore.core.databinding.ActivityLoginCredentialsBinding

class LoginCredentialsActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginCredentialsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginCredentialsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupWindowInsetsForView(binding.root)
        binding.content.signInButton.setOnClickListener {
            val email = binding.content.emailEditText.text.toString()

            if (email.isNotEmpty()) {
                val userManager = UserManager(applicationContext)

                if (!userManager.isLoggedIn) {
                    //Login in
                    Log.d("Login","Logged in : $email")
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
        builder.setPositiveButton("Ok"){ dialog, _ ->
            dialog.dismiss()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}
