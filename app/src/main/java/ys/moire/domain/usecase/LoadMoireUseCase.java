package ys.moire.domain.usecase;

import android.graphics.Color;

import com.google.gson.Gson;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ys.moire.common.config.Config;
import ys.moire.domain.model.BaseEntity;
import ys.moire.infra.prefs.Prefs;
import ys.moire.presentation.ui.view_parts.type.BaseTypes;
import ys.moire.presentation.ui.view_parts.type.Lines;

/**
 * load moire use case.
 */
@RequiredArgsConstructor(suppressConstructorProperties = true)
public class LoadMoireUseCase {

    private final Prefs prefs;

    /**
     * get setting data.
     * @param lineConfigName
     * @return Line Base Type
     */
    public BaseTypes getTypesData(@NonNull final String lineConfigName) {
        String str = prefs.get(lineConfigName, "");
        if("".equals(str)) {
            return new Lines();
        }
        return convertToObj(new Gson().fromJson(str, BaseEntity.class));
    }

    /**
     * load MireType
     * @return MoireType
     */
    public int getMoireType() {
        return prefs.get(Config.TYPE, Config.TYPE_LINE);
    }

    /**
     * load background color
     * @return color
     */
    public int getBgColor() {
        return prefs.get(Config.BG_COLOR, Color.WHITE);
    }

    /**
     * common get
     * @param key pref key
     * @return pref value
     */
    public int getInt(@NonNull final String key) {
        return prefs.get(key, -1);
    }

    private BaseTypes convertToObj(@NonNull final BaseEntity dto) {
        BaseTypes types = new BaseTypes();
        types.setColor(dto.color);
        types.setNumber(dto.number);
        types.setThick(dto.thick);
        types.setSlope(dto.slope);
        return types;
    }
}
