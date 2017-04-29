package ys.moire.infra.prefs;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import ys.moire.common.config.Config;

/**
 * Prefs.
 */

public class PrefsImpl implements Prefs {

    private final SharedPreferences preferences;

    public PrefsImpl(final Context context) {
        preferences = context
                .getApplicationContext()
                .getSharedPreferences(Config.PREF, Activity.MODE_PRIVATE);
    }

    @Override
    public void put(final String key, final String value) {
        preferences.edit().putString(key, value).apply();
    }

    @Override
    public void put(final String key, final int value) {
        preferences.edit().putInt(key, value).apply();
    }

    @Override
    public String get(final String key, final String defValue) {
        return preferences.getString(key, defValue);
    }

    @Override
    public int get(final String key, final int defValue) {
        return preferences.getInt(key, defValue);
    }
}
