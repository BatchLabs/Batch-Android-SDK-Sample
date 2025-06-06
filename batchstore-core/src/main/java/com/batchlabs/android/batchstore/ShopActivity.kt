package com.batchlabs.android.batchstore

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.batch.android.Batch
import com.batchlabs.android.batchstore.core.R
import com.batchlabs.android.batchstore.core.databinding.ActivityShopBinding
import com.batchlabs.android.batchstore.ui.articles.ArticlesFragment
import com.batchlabs.android.batchstore.ui.cart.CartFragment
import com.batchlabs.android.batchstore.ui.inbox.InboxFragment
import com.batchlabs.android.batchstore.ui.login.LoginLandingActivity
import com.batchlabs.android.batchstore.ui.settings.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class ShopActivity : BaseActivity() {

    private lateinit var currentFragment: androidx.fragment.app.Fragment

    private val TAG: String = "ShopActivity"

    private lateinit var binding : ActivityShopBinding

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_shop -> {
                val fragment = ArticlesFragment.newInstance()
                setupLayout("Shop", fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_cart -> {
                val fragment = CartFragment.newInstance()
                setupLayout("Cart", fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_inbox -> {
                val fragment = InboxFragment.newInstance()
                setupLayout("Inbox", fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_settings -> {
                val fragment = SettingsFragment.newInstance()
                setupLayout("Settings", fragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setLightStatusBar()
        setupWindowInsetsForView(binding.root)

        binding.bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        val userManager = UserManager(applicationContext)

        if (!userManager.onboardingAttempted) {
            val intent = Intent(this, LoginLandingActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        removeAllFragments()

        currentFragment = ArticlesFragment.newInstance()
        setupLayout("Shop", currentFragment)
        Batch.Push.requestNotificationPermission(this)
    }

    private fun setTitle(title: String) {
        binding.titleTextView.text = title
    }

    private fun setLightStatusBar() {
        // Set the status bar and navigation bar according to the theme
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

    override fun onBackPressed() {
        val defaultFragment = ArticlesFragment.newInstance()

        if (currentFragment.tag.equals("Shop")){
            System.exit(0)
        }

        removeAllFragments()
        currentFragment = defaultFragment
        setupLayout("Shop", currentFragment)

        binding.bottomNavigation.selectedItemId = R.id.navigation_shop
    }

    private fun removeAllFragments() {
        val fragment = supportFragmentManager
        while (fragment.backStackEntryCount > 0) {
            fragment.popBackStackImmediate()
        }
    }

    private fun setupLayout(title:String,fragment: androidx.fragment.app.Fragment){
        currentFragment = fragment

        setTitle(title)
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment, fragment, title)
                .addToBackStack(title)
                .commit()
    }
}
