package ys.moire.domain.usecase

import lombok.NonNull
import ys.moire.common.config.TypeEnum
import ys.moire.domain.model.BaseEntity
import ys.moire.infra.storage.PrefsDao
import ys.moire.presentation.ui.viewparts.type.BaseTypes

/**
 * load moire use case.
 */
class LoadMoireUseCase(private val prefsDao: PrefsDao?) {

    /**
     * get setting data.
     * @param lineConfigName
     * *
     * @return Line Base Type
     */
    fun getTypesData(@NonNull lineConfigName: String): BaseTypes =
            prefsDao!!.getTypesData(lineConfigName)

    /**
     * load MireType
     * @return MoireType
     */
    val moireType: TypeEnum
        get() = prefsDao!!.moireType

    /**
     * load background color
     * @return color
     */
    val bgColor: Int
        get() = prefsDao!!.bgColor

    /**
     * common get
     * @param key pref key
     * *
     * @return pref value
     */
    fun getInt(@NonNull key: String): Int = prefsDao!!.getInt(key)


    private fun convertToObj(@NonNull dto: BaseEntity): BaseTypes {
        val types = BaseTypes()
        types.color = dto.color
        // for migration to new version
        val previousValue = dto.number
        if (previousValue < BaseTypes.MINIMUM_VAL) {
            types.number = BaseTypes.MINIMUM_VAL
        } else {
            types.number = previousValue
        }
        types.thick = dto.thick
        types.slope = dto.slope
        return types
    }
}
