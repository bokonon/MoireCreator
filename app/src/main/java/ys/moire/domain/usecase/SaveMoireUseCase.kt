package ys.moire.domain.usecase

import lombok.NonNull
import ys.moire.common.config.TypeEnum
import ys.moire.domain.model.BaseEntity
import ys.moire.infra.storage.PrefsDao
import ys.moire.presentation.ui.viewparts.type.BaseTypes

/**
 * save moire use case.
 */
class SaveMoireUseCase(private val prefsDao: PrefsDao?) {

    /**
     * put setting data.
     * @param lineConfigName
     * *
     * @param types Line Type
     */
    fun putTypesData(@NonNull lineConfigName: String, @NonNull types: BaseTypes) {
        prefsDao!!.putTypesData(lineConfigName, types)
    }

    /**
     * store MoireType.
     * @param type Line Type
     */
    fun putMoireType(type: TypeEnum) {
        prefsDao!!.putMoireType(type)
    }

    /**
     * store background color
     * @param val color
     */
    fun putBgColor(`val`: Int) {
        prefsDao!!.putBgColor(`val`)
    }

    /**
     * common put
     * @param key pref key
     * *
     * @param val pref value
     */
    fun putInt(@NonNull key: String, `val`: Int) {
        prefsDao!!.putInt(key, `val`)
    }

    private fun convertToDto(@NonNull types: BaseTypes): BaseEntity {
        val dto = BaseEntity()
        dto.color = types.color
        dto.number = types.number
        dto.thick = types.thick
        dto.slope = types.slope
        return dto
    }
}
