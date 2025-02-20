package com.dindaka.workshops_android.data.local.sharedpreferences

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

const val NAME_PREFERENCES = "preferences_workshops"

const val KEY_EMAIL = "KEY_EMAIL"
const val KEY_UID = "KEY_UID"
const val KEY_NAME = "KEY_NAME"
const val KEY_LAT = "KEY_LAT"
const val KEY_LNG = "KEY_LNG"

@Singleton
class MyPreferences @Inject constructor(private val preferences: SharedPreferences) {
    fun getEmail() = preferences.getString(KEY_EMAIL, null)

    fun setEmail(email: String?) {
        preferences.edit().apply {
            putString(KEY_EMAIL, email)
        }.apply()
    }

    fun getUid() = preferences.getString(KEY_UID, null)

    fun setUid(uuid: String?) {
        preferences.edit().apply {
            putString(KEY_UID, uuid)
        }.apply()
    }

    fun getName() = preferences.getString(KEY_NAME, "")

    fun setName(name: String?) {
        preferences.edit().apply {
            putString(KEY_NAME, name)
        }.apply()
    }

    fun getLat() = preferences.getString(KEY_LAT, "")

    fun setLat(lat: Float?) {
        preferences.edit().apply {
            putFloat(KEY_LAT, lat ?: 0f)
        }.apply()
    }

    fun getLng() = preferences.getString(KEY_LNG, "")

    fun setLng(lng: Float?) {
        preferences.edit().apply {
            putFloat(KEY_LNG, lng ?: 0f)
        }.apply()
    }

    fun clearPreferences() {
        preferences.edit().apply{
            clear()
        }.apply()
    }
}