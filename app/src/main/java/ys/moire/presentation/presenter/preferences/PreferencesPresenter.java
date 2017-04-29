package ys.moire.presentation.presenter.preferences;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ys.moire.domain.usecase.LoadMoireUseCase;
import ys.moire.domain.usecase.SaveMoireUseCase;
import ys.moire.presentation.presenter.base.BasePresenter;
import ys.moire.presentation.ui.view_parts.type.BaseTypes;

/**
 * PreferencesPresenter.
 */
@RequiredArgsConstructor(suppressConstructorProperties = true)
public class PreferencesPresenter extends BasePresenter {

    private final LoadMoireUseCase loadMoireUseCase;

    private final SaveMoireUseCase saveMoireUseCase;

    public int getMoireType() {
        return loadMoireUseCase.getMoireType();
    }

    public void putMoireType(final int type) {
        saveMoireUseCase.putMoireType(type);
    }

    public BaseTypes loadTypesData(@NonNull final String key) {
        return loadMoireUseCase.getTypesData(key);
    }

    public void saveTypesData(@NonNull final String lineConfigName, @NonNull final BaseTypes types) {
        saveMoireUseCase.putTypesData(lineConfigName, types);
    }

    public int getBgColor() {
        return loadMoireUseCase.getBgColor();
    }

    public void putBgColor(final int color) {
        saveMoireUseCase.putBgColor(color);
    }
}
