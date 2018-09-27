package com.batchlabs.android.batchstore

import android.app.Application
import android.content.Intent
import android.util.Log
import com.batch.android.Batch
import com.batch.android.BatchActivityLifecycleHelper
import com.batch.android.Config
//import com.batchlabs.android.batchstore.UI.Code.CodeActivity
import com.batchlabs.android.batchstore.UI.Login.LoginLandingActivity
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric



class MainApplication: Application() {

    val API_KEY = BuildConfig.API_KEY

    override fun onCreate() {
        super.onCreate()
        Batch.Push.setSmallIconResourceId(R.mipmap.appicon)
        Batch.Push.setNotificationsColor(resources.getColor(R.color.colorAccent))


        Batch.setConfig(Config(API_KEY))
        registerActivityLifecycleCallbacks(BatchActivityLifecycleHelper())

        val subscriptionManager = SubscriptionManager(applicationContext)

        if (subscriptionManager.shouldSetPreferences) {
            subscriptionManager.initPreferences()
        }

        val userManager = UserManager(applicationContext)

        if (!userManager.onboardingAttempted!! || userManager.onboardingAttempted == null){
            val intent = Intent(this, LoginLandingActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }


        Fabric.with(this, Crashlytics())
    }
}