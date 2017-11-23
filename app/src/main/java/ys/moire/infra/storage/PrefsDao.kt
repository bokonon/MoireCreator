package ys.moire.infra.storage

import android.graphics.Color
import com.google.gson.Gson
import lombok.NonNull
import ys.moire.common.config.TypeEnum
import ys.moire.common.config.getType
import ys.moire.domain.model.BaseEntity
import ys.moire.infra.prefs.Prefs
import ys.moire.presentation.ui.viewparts.type.BaseTypes
import ys.moire.presentation.ui.viewparts.type.Lines

/**
 * load moire use case.
 */
class PrefsDao(private val prefs: Prefs) {

    /**
     * get setting data.
     * @param lineConfigName
     * *
     * @return Line Base Type
     */
    fun getTypesData(@NonNull lineConfigName: String): BaseTypes {
        val str = prefs.get(lineConfigName, "")
        if ("" == str) {
            return Lines()
        }
        return convertToObj(Gson().fromJson(str, BaseEntity::class.java))
    }

    /**
     * load MireType
     * @return MoireType
     */
    val moireType: TypeEnum
        get() = getType(prefs.get(ys.moire.common.config.TYPE(), ys.moire.common.config.TypeEnum.LINE.number))

    /**
     * load background color
     * @return color
     */
    val bgColor: Int
        get() = prefs.get(ys.moire.common.config.BG_COLOR(), Color.WHITE)

    /**
     * common get
     * @param key pref key
     * *
     * @return pref value
     */
    fun getInt(@NonNull key: String): Int = prefs.get(key, -1)


    /**
     * put setting data.
     * @param lineConfigName
     * *
     * @param types Line Type
     */
    fun putTypesData(@NonNull lineConfigName: String, @NonNull types: BaseTypes) {
        prefs.put(lineConfigName, Gson().toJson(convertToDto(types)))
    }

    /**
     * store MoireType.
     * @param type Line Type
     */
    fun putMoireType(type: TypeEnum) {
        prefs.put(ys.moire.common.config.TYPE(), type.number)
    }

    /**
     * store background color
     * @param val color
     */
    fun putBgColor(`val`: Int) {
        prefs.put(ys.moire.common.config.BG_COLOR(), `val`)
    }

    /**
     * common put
     * @param key pref key
     * *
     * @param val pref value
     */
    fun putInt(@NonNull key: String, `val`: Int) {
        prefs.put(key, `val`)
    }

    private fun convertToDto(@NonNull types: BaseTypes): BaseEntity {
        val dto = BaseEntity()
        dto.color = types.color
        dto.number = types.number
        dto.thick = types.thick
        dto.slope = types.slope
        return dto
    }

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