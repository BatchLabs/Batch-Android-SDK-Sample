package com.batchlabs.android.batchstore.ui.settings


import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import com.batch.android.Batch
import com.batchlabs.android.batchstore.*
import com.batchlabs.android.batchstore.ui.login.LoginLandingActivity
import com.batchlabs.android.batchstore.core.R
import kotlinx.android.synthetic.main.fragment_settings.view.*
import com.batch.android.PushNotificationType
import java.util.*


class SettingsFragment : androidx.fragment.app.Fragment() {

    val TAG:String = "SettingsFragment"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_settings,container,false)

        refreshForm(view)
        refreshInfo(view)

        return view
    }

    private fun refreshInfo(view: View) {
        view.installationID.text = "Installation ID : ${Batch.User.getInstallationID()}"
        view.lastToken.text = "Last token : ${Batch.Push.getLastKnownPushToken()}"
    }

    private fun refreshForm(view:View){
        val context = context!!
        val activity = activity!!

        val userManager = UserManager(context)
        val loggedIn = userManager.isLoggedIn
        val username = userManager.username
        val subscriptionManager = SubscriptionManager(context)

        setAllEnable(view)

        view.enablePush.setOnClickListener { notificationPreference(view) }

        val notifType = Batch.Push.getNotificationsType(context)
        if (notifType != null) {
            view.enablePush.isChecked = !notifType.contains(PushNotificationType.NONE)
        } else {
            view.enablePush.isChecked = true
        }


        if (!loggedIn) {
            view.flashSales.isChecked = false
            view.flashSales.isEnabled = false

            view.login.text = "Touch to login"
        } else {
            view.login.isChecked = true
            view.login.text = "Logged in with $username"

            view.flashSales.isChecked = subscriptionManager.flashSales!!
            view.flashSales.isEnabled = true
        }

        val suggestedContentEnabled = subscriptionManager.suggestedContent
        view.suggestedContent.isChecked = suggestedContentEnabled!!
        view.suggestionFashion.isChecked = subscriptionManager.isSubscribedToSuggestion(suggestionCategoryFashion)
        view.suggestionMensWear.isChecked = subscriptionManager.isSubscribedToSuggestion(suggestionCategoryMensWear)
        view.suggestionOther.isChecked = subscriptionManager.isSubscribedToSuggestion(suggestionCategoryOther)

        if (!suggestedContentEnabled){
            view.suggestionFashion.isEnabled = false
            view.suggestionMensWear.isEnabled = false
            view.suggestionOther.isEnabled = false
        }


        view.login.setOnClickListener {
            if (loggedIn) {
                AlertDialog.Builder(activity)
                        .setTitle("Do you want logout ?")
                        .setPositiveButton("Yes") { _, _ ->
                            userManager.logout()
                            refreshForm(view)
                        }
                        .setNegativeButton(android.R.string.cancel) { dialog, which -> view.login.isChecked = true }
                        .show()
            } else {
                val intent = Intent(activity, LoginLandingActivity::class.java)
                startActivity(intent)
                view.login.isChecked = true
            }
        }

        view.flashSales.setOnClickListener {
            subscriptionManager.flashSales = view.flashSales.isChecked
            refreshForm(view)
        }

        view.suggestedContent.setOnClickListener {
            subscriptionManager.suggestedContent = view.suggestedContent.isChecked
            refreshForm(view)
        }


        view.suggestionFashion.setOnClickListener { suggestionCategoryToggled(view,suggestionCategoryFashion) }
        view.suggestionMensWear.setOnClickListener { suggestionCategoryToggled(view, suggestionCategoryMensWear) }
        view.suggestionOther.setOnClickListener { suggestionCategoryToggled(view, suggestionCategoryOther) }

        view.printBatchUser.setOnClickListener {
            Log.d(TAG,"Batch Store Debug information --")
            Log.d(TAG,"InstallID: ${Batch.User.getInstallationID()}")
            Log.d(TAG,"Last known push token: ${Batch.Push.getLastKnownPushToken()}")
        }
    }

    private fun notificationPreference(view: View) {
        val switch = view.enablePush

        if (switch.isChecked) {
            val set = EnumSet.of(PushNotificationType.ALERT)
            set.add(PushNotificationType.SOUND)
            set.add(PushNotificationType.LIGHTS)

            Batch.Push.setNotificationsType(set)
        } else {
            val set = EnumSet.of(PushNotificationType.NONE) // Disable all notifications
            Batch.Push.setNotificationsType(set)
        }
    }

    private fun suggestionCategoryToggled(view:View, category:String) {
        var topicName:String?
        var switch:Switch?

        when(category) {
            suggestionCategoryFashion -> {
                topicName = suggestionCategoryFashion
                switch = view.suggestionFashion
            }
            suggestionCategoryMensWear -> {
                topicName = suggestionCategoryMensWear
                switch = view.suggestionMensWear
            }
            suggestionCategoryOther -> {
                topicName = suggestionCategoryOther
                switch = view.suggestionOther
            }
            else -> {
                //Do nothing
                topicName = null
                switch = null
            }
        }

        if (topicName != null && switch != null) {
            Log.d(TAG,"$topicName ${switch.isChecked}")
            SubscriptionManager(context!!).toggleSuggestionCategory(topicName,switch.isChecked)
        }
    }

    private fun setAllEnable(view:View){
        view.suggestedContent.isEnabled = true
        view.suggestionFashion.isEnabled = true
        view.suggestionMensWear.isEnabled = true
        view.suggestionOther.isEnabled = true
        view.flashSales.isEnabled = true
    }

    companion object {
        fun newInstance(): SettingsFragment {
            return SettingsFragment()
        }
    }
}
