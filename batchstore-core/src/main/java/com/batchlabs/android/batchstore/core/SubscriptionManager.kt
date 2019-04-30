package com.batchlabs.android.batchstore

import android.content.Context
import com.batch.android.Batch


private val defaultsSetKey = "defaults_set"

private val flashSalesKey = "wants_flash_sales"

private val suggestedContentKey = "wants_suggested_content"
private val suggestionCategoriesKeys = "suggestion_topics"
private val sourceSubscriptionListKey = "subscribed_sources"

val suggestionCategoryFashion = "fashion"
val suggestionCategoryMensWear = "menswear"
val suggestionCategoryOther = "other"

class SubscriptionManager (val context:Context) {
    val TAG = "SubscriptionManager"

    val automaticBatchSync:Boolean = true

    var shouldSetPreferences: Boolean = false
        get() = !PreferenceHelper().getBoolPreference(context,defaultsSetKey,false)

    var flashSales: Boolean?
        get() = PreferenceHelper().getBoolPreference(context,flashSalesKey, false)
        set(newValue) {
            if (newValue != null) {
                PreferenceHelper().setBoolPreference(context, flashSalesKey, newValue)
                if (automaticBatchSync) {
                    syncDataWithBatch()
                }
            }
        }

    var suggestedContent: Boolean?
        get() = PreferenceHelper().getBoolPreference(context, suggestedContentKey, true)
        set(newValue) {
            if (newValue != null) {
                PreferenceHelper().setBoolPreference(context, suggestedContentKey, newValue)
                if (automaticBatchSync) {
                    syncDataWithBatch()
                }
            }
        }

    fun isSubscribedToSource(name: String, enable: Boolean) {
        toggleArrayValue(suggestionCategoriesKeys, name, enable)
    }

    fun isSubscribedToSuggestion(name: String): Boolean {
        val subscribedSuggestions = PreferenceHelper().getStringArray(context,suggestionCategoriesKeys)
        if (subscribedSuggestions.size > 0) {
            return subscribedSuggestions.contains(name)
        }

        return false
    }

    fun toggleSuggestionCategory(name: String, enable: Boolean) {
        toggleArrayValue(suggestionCategoriesKeys,name,enable)
    }

    private fun toggleArrayValue(arrayName: String, value: String, enalbe: Boolean) {
        val pref = PreferenceHelper()

        var array:ArrayList<String> = ArrayList()
        if (!pref.getStringArray(context, arrayName).isEmpty()) {
            array = pref.getStringArray(context, arrayName)
        }

        if (enalbe) {
            if (!array.contains(value)){
                array.add(value)
            }
        } else {
            array.remove(value)
        }

        pref.setStringArray(context,arrayName,array)

        if (automaticBatchSync) {
            syncDataWithBatch()
        }
    }

    fun initPreferences(){
        val pref = PreferenceHelper()
        pref.setBoolPreference(context, defaultsSetKey, true)
        pref.setBoolPreference(context, flashSalesKey, true)

        val suggestionList = ArrayList<String>()
        suggestionList.add(suggestionCategoryFashion)
        suggestionList.add(suggestionCategoryMensWear)
        suggestionList.add(suggestionCategoryOther)
        pref.setStringArray(context, suggestionCategoriesKeys, suggestionList)

        if (automaticBatchSync) {
            syncDataWithBatch()
        }
    }

    fun syncDataWithBatch(){
        val pref = PreferenceHelper()
        val userManager = UserManager(context)

        val editor = Batch.User.editor()
        editor.setAttribute(flashSalesKey, pref.getBoolPreference(context, flashSalesKey,false))
        editor.setAttribute(suggestedContentKey, pref.getBoolPreference(context, suggestedContentKey,false))

        editor.clearTagCollection(suggestionCategoriesKeys)

        pref.getStringArray(context, suggestionCategoriesKeys).forEach { category ->
            editor.addTag(suggestionCategoriesKeys, category)
        }

        editor.clearTagCollection(sourceSubscriptionListKey)

        if (userManager.isLoggedIn) {
            pref.getStringArray(context, sourceSubscriptionListKey).forEach { category ->
                editor.addTag(sourceSubscriptionListKey, category)
            }
        }

        editor.save()
    }
}