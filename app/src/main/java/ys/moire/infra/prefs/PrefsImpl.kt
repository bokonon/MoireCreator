package ys.moire.infra.prefs

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

/**
 * Prefs.
 */

class PrefsImpl(context: Context) : Prefs {

    private val preferences: SharedPreferences = context
            .applicationContext
            .getSharedPreferences(ys.moire.common.config.PREF(), Activity.MODE_PRIVATE)

    override fun put(key: String, value: String) {
        preferences.edit().putString(key, value).apply()
    }

    override fun put(key: String, value: Int) {
        preferences.edit().putInt(key, value).apply()
    }

    override fun get(key: String, defValue: String): String = preferences.getString(key, defValue)

    override fun get(key: String, defValue: Int): Int = preferences.getInt(key, defValue)
}
