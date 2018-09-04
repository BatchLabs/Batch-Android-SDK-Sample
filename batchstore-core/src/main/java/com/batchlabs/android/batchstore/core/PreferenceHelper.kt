package com.batchlabs.android.batchstore

import android.content.Context
import android.preference.PreferenceManager
import android.text.TextUtils
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.reflect.TypeToken
import com.google.gson.Gson


class PreferenceHelper {

    fun removeStringPreference(context: Context,key: String){
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        preferences.edit().remove(key).commit()
    }

    fun getStringPreference(context: Context, key: String): String? {
        var value: String? = null
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        if (preferences != null) {
            value = preferences.getString(key, null)
        }
        return value
    }

    fun setStringPreference(context: Context, key: String, value: String): Boolean {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        if (preferences != null && !TextUtils.isEmpty(key)) {
            val editor = preferences.edit()
            editor.putString(key, value)
            return editor.commit()
        }
        return false
    }

    fun getBoolreference(context: Context, key: String, defaultValue:Boolean): Boolean? {
        var value: Boolean? = defaultValue
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        if (preferences != null) {
            value = preferences.getBoolean(key,defaultValue)
        }
        return value
    }

    fun setBoolPreference(context: Context, key: String, value: Boolean): Boolean {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        if (preferences != null && !TextUtils.isEmpty(key)) {
            val editor = preferences.edit()
            editor.putBoolean(key, value)
            return editor.commit()
        }
        return false
    }

    fun setStringArray(context: Context, key: String, list: ArrayList<String>) : Boolean {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = preferences.edit()
        val gson = Gson()
        val json = gson.toJson(list)
        editor.putString(key, json)
        return editor.commit()
    }

    fun getStringArray(context: Context, key: String): ArrayList<String> {
        val arrayList = ArrayList<String>()

        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val json = preferences.getString(key, null)

        val gson = Gson()
        val array = gson.fromJson(json, Array<String>::class.java)

        if (array != null && !array.isEmpty()) {
            array.forEach { item -> arrayList.add(item) }
        }

        return arrayList
    }
}