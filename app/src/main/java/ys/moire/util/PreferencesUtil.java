package ys.moire.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

import com.google.gson.Gson;

import ys.moire.config.Config;
import ys.moire.dto.BaseDto;
import ys.moire.type.BaseTypes;
import ys.moire.type.Lines;

/**
 * SharedPreferenceのUtilクラス.
 */
public class PreferencesUtil {

    private static final String TAG = "PreferencesUtil";

    /**
     * 設定情報を保存します
     * @param activityContext
     * @param types
     * @param lineConfigName
     */
    public static void saveTypesData(final Context activityContext, final BaseTypes types, final String lineConfigName) {
        SharedPreferences setting = activityContext
                .getApplicationContext()
                .getSharedPreferences(Config.PREF, Activity.MODE_PRIVATE);
        setting.edit().putString(lineConfigName, new Gson().toJson(convertToDto(types))).apply();
    }

    /**
     * 設定情報を読み出します
     * @param activityContext
     * @param lineConfigName
     * @return BaseTypes 図形集合の基底クラス
     */
    public static BaseTypes loadTypesData(final Context activityContext, final String lineConfigName) {
        SharedPreferences setting = activityContext
                .getApplicationContext()
                .getSharedPreferences(Config.PREF, Activity.MODE_PRIVATE);
        String str = setting.getString(lineConfigName, "");
        if("".equals(str)) {
            return new Lines();
        }
        return convertToObj(new Gson().fromJson(str, BaseDto.class));
    }

    /**
     * MoireTypeを保存します
     * @param activityContext
     * @param type
     */
    public static void putMoireType(final Context activityContext, final int type) {
        SharedPreferences setting = activityContext
                .getApplicationContext()
                .getSharedPreferences(Config.PREF, Activity.MODE_PRIVATE);
        setting.edit().putInt(Config.TYPE, type).apply();
    }

    /**
     * MireTypeを読み出します
     * @param activityContext
     * @return
     */
    public static int getMoireType(final Context activityContext) {
        SharedPreferences setting = activityContext
                .getApplicationContext()
                .getSharedPreferences(Config.PREF, Activity.MODE_PRIVATE);
        return setting.getInt(Config.TYPE, Config.TYPE_LINE);
    }

    /**
     * バックグランドカラーを保存します
     * @param activityContext
     * @param val
     */
    public static void putBgColor(final Context activityContext, final int val) {
        SharedPreferences setting = activityContext
                .getApplicationContext()
                .getSharedPreferences(Config.PREF, Activity.MODE_PRIVATE);
        setting.edit().putInt(Config.BG_COLOR, val).apply();
    }

    /**
     * バックグランドカラーを読み込みます
     * @param activityContext
     * @return
     */
    public static int getBgColor(final Context activityContext) {
        SharedPreferences setting = activityContext
                .getApplicationContext()
                .getSharedPreferences(Config.PREF, Activity.MODE_PRIVATE);
        return setting.getInt(Config.BG_COLOR, Color.WHITE);
    }

    /**
     * 汎用的なPut
     * @param activityContext
     * @param key
     * @param val
     */
    public void putInt(final Context activityContext, final String key, final int val) {
        SharedPreferences setting = activityContext
                .getApplicationContext()
                .getSharedPreferences(Config.PREF, Activity.MODE_PRIVATE);
        setting.edit().putInt(key, val).apply();
    }

    /**
     * 汎用的なGet
     * @param activityContext
     * @param key
     * @return
     */
    public static int getInt(final Context activityContext, final String key) {
        SharedPreferences setting = activityContext
                .getApplicationContext()
                .getSharedPreferences(Config.PREF, Activity.MODE_PRIVATE);
        return setting.getInt(key, -1);
    }

    private static BaseDto convertToDto(final BaseTypes types) {
        BaseDto dto = new BaseDto();
        dto.color = types.getColor();
        dto.number = types.getNumber();
        dto.thick = types.getThick();
        dto.slope = types.getSlope();
        return dto;
    }

    private static BaseTypes convertToObj(final BaseDto dto) {
        BaseTypes types = new BaseTypes();
        types.setColor(dto.color);
        types.setNumber(dto.number);
        types.setThick(dto.thick);
        types.setSlope(dto.slope);
        return types;
    }

}
