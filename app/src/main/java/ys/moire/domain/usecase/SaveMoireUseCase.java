package ys.moire.domain.usecase;

import com.google.gson.Gson;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ys.moire.common.config.Config;
import ys.moire.domain.model.BaseEntity;
import ys.moire.infra.prefs.Prefs;
import ys.moire.presentation.ui.view_parts.type.BaseTypes;

/**
 * save moire use case.
 */
@RequiredArgsConstructor(suppressConstructorProperties = true)
public class SaveMoireUseCase {

    private final Prefs prefs;

    /**
     * put setting data.
     * @param lineConfigName
     * @param types Line Type
     */
    public void putTypesData(@NonNull final String lineConfigName, @NonNull final BaseTypes types) {
        prefs.put(lineConfigName, new Gson().toJson(convertToDto(types)));
    }

    /**
     * store MoireType.
     * @param type Line Type
     */
    public void putMoireType(final int type) {
        prefs.put(Config.TYPE, type);
    }

    /**
     * store background color
     * @param val color
     */
    public void putBgColor(final int val) {
        prefs.put(Config.BG_COLOR, val);
    }

    /**
     * common put
     * @param key pref key
     * @param val pref value
     */
    public void putInt(@NonNull final String key, final int val) {
        prefs.put(key, val);
    }

    private BaseEntity convertToDto(@NonNull final BaseTypes types) {
        BaseEntity dto = new BaseEntity();
        dto.color = types.getColor();
        dto.number = types.getNumber();
        dto.thick = types.getThick();
        dto.slope = types.getSlope();
        return dto;
    }
}
