package ys.moire.ui.view.preferences;

import android.content.Context;

import ys.moire.domain.SharedPreferencesManager;
import ys.moire.type.BaseTypes;
import ys.moire.ui.base.BasePresenter;

/**
 * PreferencesPresenter.
 */

public class PreferencesPresenter extends BasePresenter<PreferencesMvpView> {

    private final Context activityContext;
    private SharedPreferencesManager preferencesManager;

    public PreferencesPresenter(final Context activityContext) {
        this.activityContext = activityContext;
        preferencesManager = new SharedPreferencesManager(activityContext);
    }

    public int getMoireType() {
        return preferencesManager.getMoireType();
    }

    public void putMoireType(final int type) {
        preferencesManager.putMoireType(type);
    }

    public BaseTypes loadTypesData(final String key) {
        return preferencesManager.loadTypesData(key);
    }

    public void saveTypesData(final String lineConfigName, final BaseTypes types) {
        preferencesManager.saveTypesData(lineConfigName, types);
    }

    public int getBgColor() {
        return preferencesManager.getBgColor();
    }

    public void putBgColor(final int color) {
        preferencesManager.putBgColor(color);
    }
}
