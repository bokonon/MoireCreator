package ys.moire.data;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import ys.moire.config.Config;

/**
 * PreferencesHelper.
 */

public class PreferencesHelper {

    private final SharedPreferences preferences;

    public PreferencesHelper(final Context activityContext) {
        preferences = activityContext
                .getApplicationContext()
                .getSharedPreferences(Config.PREF, Activity.MODE_PRIVATE);
    }

    public void put(final String key, final String value) {
        preferences.edit().putString(key, value).apply();
    }

    public void put(final String key, final int value) {
        preferences.edit().putInt(key, value).apply();
    }

    public String get(final String key, final String defValue) {
        return preferences.getString(key, defValue);
    }

    public int get(final String key, final int defValue) {
        return preferences.getInt(key, defValue);
    }
}
