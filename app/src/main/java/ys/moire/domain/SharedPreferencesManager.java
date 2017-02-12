package ys.moire.domain;

import android.content.Context;
import android.graphics.Color;

import com.google.gson.Gson;

import ys.moire.config.Config;
import ys.moire.data.PreferencesHelper;
import ys.moire.data.model.BaseEntity;
import ys.moire.type.BaseTypes;
import ys.moire.type.Lines;

/**
 * SharedPreferencesManager.
 */

public class SharedPreferencesManager {

    private PreferencesHelper preferencesHelper;

    public SharedPreferencesManager(final Context activityContext) {
        preferencesHelper = new PreferencesHelper(activityContext);
    }

    /**
     * store setting data.
     * @param lineConfigName
     * @param types Line Type
     */
    public void saveTypesData(final String lineConfigName, final BaseTypes types) {
        preferencesHelper.put(lineConfigName, new Gson().toJson(convertToDto(types)));
    }

    /**
     * load setting data.
     * @param lineConfigName
     * @return Line Base Type
     */
    public BaseTypes loadTypesData(final String lineConfigName) {
        String str = preferencesHelper.get(lineConfigName, "");
        if("".equals(str)) {
            return new Lines();
        }
        return convertToObj(new Gson().fromJson(str, BaseEntity.class));
    }

    /**
     * store MoireType.
     * @param type Line Type
     */
    public void putMoireType(final int type) {
        preferencesHelper.put(Config.TYPE, type);
    }

    /**
     * load MireType
     * @return MoireType
     */
    public int getMoireType() {
        return preferencesHelper.get(Config.TYPE, Config.TYPE_LINE);
    }

    /**
     * store background color
     * @param val color
     */
    public void putBgColor(final int val) {
        preferencesHelper.put(Config.BG_COLOR, val);
    }

    /**
     * load background color
     * @return color
     */
    public int getBgColor() {
        return preferencesHelper.get(Config.BG_COLOR, Color.WHITE);
    }

    /**
     * common put
     * @param key pref key
     * @param val pref value
     */
    public void putInt(final String key, final int val) {
        preferencesHelper.put(key, val);
    }

    /**
     * common get
     * @param key pref key
     * @return pref value
     */
    public int getInt(final String key) {
        return preferencesHelper.get(key, -1);
    }

    private BaseEntity convertToDto(final BaseTypes types) {
        BaseEntity dto = new BaseEntity();
        dto.color = types.getColor();
        dto.number = types.getNumber();
        dto.thick = types.getThick();
        dto.slope = types.getSlope();
        return dto;
    }

    private BaseTypes convertToObj(final BaseEntity dto) {
        BaseTypes types = new BaseTypes();
        types.setColor(dto.color);
        types.setNumber(dto.number);
        types.setThick(dto.thick);
        types.setSlope(dto.slope);
        return types;
    }
}
