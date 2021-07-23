package ys.moire.presentation.presenter.preferences

import androidx.annotation.NonNull
import ys.moire.common.config.TypeEnum
import ys.moire.domain.usecase.LoadMoireUseCase
import ys.moire.domain.usecase.SaveMoireUseCase
import ys.moire.presentation.presenter.base.BasePresenter
import ys.moire.presentation.ui.viewparts.type.BaseTypes

/**
 * PreferencesPresenter.
 */
class PreferencesPresenter(private val loadMoireUseCase: LoadMoireUseCase, private val saveMoireUseCase: SaveMoireUseCase) : BasePresenter() {

    val moireType: TypeEnum
        get() = loadMoireUseCase.moireType

    fun putMoireType(type: TypeEnum) {
        saveMoireUseCase.putMoireType(type)
    }

    fun loadTypesData(@NonNull key: String): BaseTypes = loadMoireUseCase.getTypesData(key)

    fun saveTypesData(@NonNull lineConfigName: String, @NonNull types: BaseTypes) {
        saveMoireUseCase.putTypesData(lineConfigName, types)
    }

    val bgColor: Int
        get() = loadMoireUseCase.bgColor

    fun putBgColor(color: Int) {
        saveMoireUseCase.putBgColor(color)
    }
}
