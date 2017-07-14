package ys.moire.domain.usecase

import android.graphics.Color
import com.google.gson.Gson
import lombok.NonNull
import ys.moire.domain.model.BaseEntity
import ys.moire.infra.prefs.Prefs
import ys.moire.presentation.ui.view_parts.type.BaseTypes
import ys.moire.presentation.ui.view_parts.type.Lines

/**
 * load moire use case.
 */
class LoadMoireUseCase(private val prefs: Prefs?) {

    /**
     * get setting data.
     * @param lineConfigName
     * *
     * @return Line Base Type
     */
    fun getTypesData(@NonNull lineConfigName: String): BaseTypes {
        val str = prefs!!.get(lineConfigName, "")
        if ("" == str) {
            return Lines()
        }
        return convertToObj(Gson().fromJson(str, BaseEntity::class.java))
    }

    /**
     * load MireType
     * @return MoireType
     */
    val moireType: Int
        get() = prefs!!.get(ys.moire.common.config.TYPE(), ys.moire.common.config.TYPE_LINE())

    /**
     * load background color
     * @return color
     */
    val bgColor: Int
        get() = prefs!!.get(ys.moire.common.config.BG_COLOR(), Color.WHITE)

    /**
     * common get
     * @param key pref key
     * *
     * @return pref value
     */
    fun getInt(@NonNull key: String): Int {
        return prefs!!.get(key, -1)
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
