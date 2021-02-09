package jmapps.arabicinyourhands.ui.preferences

import android.content.SharedPreferences

data class SharedLocalProperties(val preferences: SharedPreferences) {

    fun saveIntValue(key: String, currentValue: Int) {
        preferences.edit().putInt(key, currentValue).apply()
    }

    fun getIntValue(key: String, defaultValue: Int) : Int {
        return preferences.getInt(key, defaultValue)
    }

    fun saveStringValue(key: String, currentValue: String) {
        preferences.edit().putString(key, currentValue).apply()
    }

    fun getStringValue(key: String, defaultValue: String): String? {
        return preferences.getString(key, defaultValue)
    }

    fun saveBooleanValue(key: String, currentValue: Boolean) {
        preferences.edit().putBoolean(key, currentValue).apply()
    }

    fun getBooleanValue(key: String, defaultValue: Boolean): Boolean {
        return preferences.getBoolean(key, defaultValue)
    }
}
