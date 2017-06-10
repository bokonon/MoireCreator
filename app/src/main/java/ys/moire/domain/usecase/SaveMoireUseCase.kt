package ys.moire.domain.usecase

import com.google.gson.Gson
import lombok.NonNull
import ys.moire.domain.model.BaseEntity
import ys.moire.infra.prefs.Prefs
import ys.moire.presentation.ui.view_parts.type.BaseTypes

/**
 * save moire use case.
 */
class SaveMoireUseCase(private val prefs: Prefs?) {

    /**
     * put setting data.
     * @param lineConfigName
     * *
     * @param types Line Type
     */
    fun putTypesData(@NonNull lineConfigName: String, @NonNull types: BaseTypes) {
        prefs!!.put(lineConfigName, Gson().toJson(convertToDto(types)))
    }

    /**
     * store MoireType.
     * @param type Line Type
     */
    fun putMoireType(type: Int) {
        prefs!!.put(ys.moire.common.config.TYPE(), type)
    }

    /**
     * store background color
     * @param val color
     */
    fun putBgColor(`val`: Int) {
        prefs!!.put(ys.moire.common.config.BG_COLOR(), `val`)
    }

    /**
     * common put
     * @param key pref key
     * *
     * @param val pref value
     */
    fun putInt(@NonNull key: String, `val`: Int) {
        prefs!!.put(key, `val`)
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
