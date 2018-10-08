package com.batchlabs.android.batchstore

import android.app.Application
import com.batch.android.Batch
import com.batch.android.BatchActivityLifecycleHelper
import com.batch.android.Config
import com.batch.android.PushNotificationType
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric
import java.util.*


class MainApplication: Application() {

    val API_KEY = BuildConfig.API_KEY

    override fun onCreate() {
        super.onCreate()
        Batch.Push.setSmallIconResourceId(R.drawable.ic_notification_smallicon)
        Batch.Push.setNotificationsColor(resources.getColor(R.color.colorAccent))


        if (Batch.Push.getNotificationsType(applicationContext) == null) {
            val set = EnumSet.of(PushNotificationType.LIGHTS)
            set.add(PushNotificationType.SOUND)
            set.add(PushNotificationType.ALERT)

            Batch.Push.setNotificationsType(set)
        }

        Batch.setConfig(Config(API_KEY))
        registerActivityLifecycleCallbacks(BatchActivityLifecycleHelper())

        val subscriptionManager = SubscriptionManager(applicationContext)

        if (subscriptionManager.shouldSetPreferences) {
            subscriptionManager.initPreferences()
        }

        Fabric.with(this, Crashlytics())
    }
}