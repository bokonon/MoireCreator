package ys.moire.presentation.presenter.preferences

import lombok.NonNull
import ys.moire.domain.usecase.LoadMoireUseCase
import ys.moire.domain.usecase.SaveMoireUseCase
import ys.moire.presentation.presenter.base.BasePresenter
import ys.moire.presentation.ui.view_parts.type.BaseTypes

/**
 * PreferencesPresenter.
 */
class PreferencesPresenter(private val loadMoireUseCase: LoadMoireUseCase?, private val saveMoireUseCase: SaveMoireUseCase?) : BasePresenter() {

    val moireType: Int
        get() = loadMoireUseCase!!.moireType

    fun putMoireType(type: Int) {
        saveMoireUseCase!!.putMoireType(type)
    }

    fun loadTypesData(@NonNull key: String): BaseTypes {
        return loadMoireUseCase!!.getTypesData(key)
    }

    fun saveTypesData(@NonNull lineConfigName: String, @NonNull types: BaseTypes) {
        saveMoireUseCase!!.putTypesData(lineConfigName, types)
    }

    val bgColor: Int
        get() = loadMoireUseCase!!.bgColor

    fun putBgColor(color: Int) {
        saveMoireUseCase!!.putBgColor(color)
    }
}
