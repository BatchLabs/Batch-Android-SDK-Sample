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
import com.batchlabs.android.batchstore.core.databinding.FragmentSettingsBinding


class SettingsFragment : androidx.fragment.app.Fragment() {

    private val TAG:String = "SettingsFragment"

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val view = binding.root
        refreshForm()
        refreshInfo()
        return view
    }

    private fun refreshInfo() {
        binding.installationID.text = "Installation ID : ${Batch.User.getInstallationID()}"
        binding.lastToken.text = "Last token : ${Batch.Push.getRegistration()?.token}"
    }

    private fun refreshForm(){
        val context = requireContext()
        val activity = requireActivity()

        val userManager = UserManager(context)
        val loggedIn = userManager.isLoggedIn
        val username = userManager.username
        val subscriptionManager = SubscriptionManager(context)

        setAllEnable()

        binding.enablePush.setOnClickListener { notificationPreference() }

        binding.enablePush.isChecked = Batch.Push.shouldShowNotifications(context)

        if (!loggedIn) {
            binding.flashSales.isChecked = false
            binding.flashSales.isEnabled = false

            binding.login.text = "Touch to login"
        } else {
            binding.login.isChecked = true
            binding.login.text = "Logged in with $username"

            binding.flashSales.isChecked = subscriptionManager.flashSales!!
            binding.flashSales.isEnabled = true
        }

        val suggestedContentEnabled = subscriptionManager.suggestedContent
        binding.suggestedContent.isChecked = suggestedContentEnabled!!
        binding.suggestionFashion.isChecked = subscriptionManager.isSubscribedToSuggestion(suggestionCategoryFashion)
        binding.suggestionMensWear.isChecked = subscriptionManager.isSubscribedToSuggestion(suggestionCategoryMensWear)
        binding.suggestionOther.isChecked = subscriptionManager.isSubscribedToSuggestion(suggestionCategoryOther)

        if (!suggestedContentEnabled){
            binding.suggestionFashion.isEnabled = false
            binding.suggestionMensWear.isEnabled = false
            binding.suggestionOther.isEnabled = false
        }


        binding.login.setOnClickListener {
            if (loggedIn) {
                AlertDialog.Builder(activity)
                        .setTitle("Do you want logout ?")
                        .setPositiveButton("Yes") { _, _ ->
                            userManager.logout()
                            refreshForm()
                        }
                        .setNegativeButton(android.R.string.cancel) { dialog, which -> binding.login.isChecked = true }
                        .show()
            } else {
                val intent = Intent(activity, LoginLandingActivity::class.java)
                startActivity(intent)
                binding.login.isChecked = true
            }
        }

        binding.flashSales.setOnClickListener {
            subscriptionManager.flashSales = binding.flashSales.isChecked
            refreshForm()
        }

        binding.suggestedContent.setOnClickListener {
            subscriptionManager.suggestedContent = binding.suggestedContent.isChecked
            refreshForm()
        }


        binding.suggestionFashion.setOnClickListener { suggestionCategoryToggled(suggestionCategoryFashion) }
        binding.suggestionMensWear.setOnClickListener { suggestionCategoryToggled(suggestionCategoryMensWear) }
        binding.suggestionOther.setOnClickListener { suggestionCategoryToggled(suggestionCategoryOther) }

        binding.printBatchUser.setOnClickListener {
            Log.d(TAG,"Batch Store Debug information --")
            Log.d(TAG,"InstallID: ${Batch.User.getInstallationID()}")
            Log.d(TAG,"Last known push token: ${Batch.Push.getRegistration()?.token}")
        }
    }

    private fun notificationPreference() {
        if (binding.enablePush.isChecked) {
            Batch.Push.setShowNotifications(true)
        } else {
            Batch.Push.setShowNotifications(false)
        }
    }

    private fun suggestionCategoryToggled(category:String) {
        var topicName:String?
        var switch:Switch?

        when(category) {
            suggestionCategoryFashion -> {
                topicName = suggestionCategoryFashion
                switch = binding.suggestionFashion
            }
            suggestionCategoryMensWear -> {
                topicName = suggestionCategoryMensWear
                switch = binding.suggestionMensWear
            }
            suggestionCategoryOther -> {
                topicName = suggestionCategoryOther
                switch = binding.suggestionOther
            }
            else -> {
                //Do nothing
                topicName = null
                switch = null
            }
        }

        if (topicName != null && switch != null) {
            Log.d(TAG,"$topicName ${switch.isChecked}")
            SubscriptionManager(requireContext()).toggleSuggestionCategory(topicName,switch.isChecked)
        }
    }

    private fun setAllEnable(){
        binding.suggestedContent.isEnabled = true
        binding.suggestionFashion.isEnabled = true
        binding.suggestionMensWear.isEnabled = true
        binding.suggestionOther.isEnabled = true
        binding.flashSales.isEnabled = true
    }

    companion object {
        fun newInstance(): SettingsFragment {
            return SettingsFragment()
        }
    }
}
