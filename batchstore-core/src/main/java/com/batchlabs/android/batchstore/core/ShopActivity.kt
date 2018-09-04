package com.batchlabs.android.batchstore.core

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.batchlabs.android.batchstore.UI.Articles.ArticlesFragment
import com.batchlabs.android.batchstore.UI.Inbox.InboxFragment
import com.batchlabs.android.batchstore.core.UI.Cart.CartFragment
import com.batchlabs.android.batchstore.core.UI.Settings.SettingsFragment
import kotlinx.android.synthetic.main.activity_shop.*

class ShopActivity : AppCompatActivity() {

    private lateinit var currentFragment: Fragment
    private val TAG:String = "ShopActivity"

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
        setContentView(R.layout.activity_shop)
        bottom_navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        removeAllFragments()

        currentFragment = ArticlesFragment.newInstance()
        setupLayout("Shop", currentFragment)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        removeAllFragments()

        currentFragment = ArticlesFragment.newInstance()
        setupLayout("Shop", currentFragment)

        bottom_navigation.selectedItemId = R.id.navigation_shop
    }

    private fun removeAllFragments() {
        val fragment = supportFragmentManager
        while (fragment.backStackEntryCount > 0) {
            fragment.popBackStackImmediate()
        }
    }

    private fun setupLayout(title:String,fragment:Fragment){
        currentFragment = fragment

        setTitle(title)
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment, fragment, title)
                .addToBackStack(title)
                .commit()
    }
}
