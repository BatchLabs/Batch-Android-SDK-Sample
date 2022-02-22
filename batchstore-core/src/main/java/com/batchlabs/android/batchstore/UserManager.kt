package com.batchlabs.android.batchstore

import android.content.Context
import com.batch.android.Batch

private val onboardingAttemptedKey = "onboardingAttempted"
private val usernameKey = "accountUsername"

class UserManager (context:Context) {

    var context = context

    var isLoggedIn:Boolean = false
        get() = username != null

    var onboardingAttempted: Boolean
        get() = PreferenceHelper().getBoolPreference(context,onboardingAttemptedKey,false)
        set(newValue) {
            PreferenceHelper().setBoolPreference(context,onboardingAttemptedKey, newValue)
            syncBatchUserInfo()
        }

    var username: String?
        get() = PreferenceHelper().getStringPreference(context,usernameKey)
        set(newValue) {
            if (newValue != null) {
                PreferenceHelper().setStringPreference(context,usernameKey, newValue)
                syncBatchUserInfo()
            }
        }

    fun login(username:String) {
        this.username = username
    }

    fun logout() {
        PreferenceHelper().removeStringPreference(context, usernameKey)
    }

    private fun syncBatchUserInfo(){
        val username = this.username
        val editor = Batch.User.editor()
        editor.setIdentifier(username)
        editor.save()

        SubscriptionManager(context).syncDataWithBatch()
    }
}