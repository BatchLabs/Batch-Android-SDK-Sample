package com.batchlabs.android.batchstore

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat

open class BaseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLightStatusBar()
    }

    private fun setLightStatusBar() {
        // Set the light status bar and navigation
        val windowInsetsController = WindowCompat.getInsetsController(
            window,
            window.decorView
        )
        windowInsetsController.isAppearanceLightStatusBars = true
        windowInsetsController.isAppearanceLightNavigationBars = true

        ViewCompat.setOnApplyWindowInsetsListener(
            window.decorView
        ) { view: View, windowInsets: WindowInsetsCompat ->
            view.setBackgroundColor(Color.WHITE)
            windowInsets
        }
    }

    protected fun setupWindowInsetsForView(view: View) {
        WindowCompat.setDecorFitsSystemWindows(window, true)
        ViewCompat.setOnApplyWindowInsetsListener(
            view
        ) { v: View, windowInsets: WindowInsetsCompat ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            val params = v.layoutParams as MarginLayoutParams
            params.leftMargin = insets.left
            params.bottomMargin = insets.bottom
            params.rightMargin = insets.right
            params.topMargin = insets.top
            v.layoutParams = params
            WindowInsetsCompat.CONSUMED
        }
    }
}
