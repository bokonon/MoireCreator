package ys.moire.common.config

/**
 * Config class.
 */

fun getType(number: Int): TypeEnum = TypeEnum.values().first { number == it.number }

/** MoreType  */
enum class TypeEnum(val number: Int,
                    val description: String) {
    LINE(0, "Line"),
    CIRCLE(1, "Circle"),
    RECT(2, "Rect"),
    HEART(3, "Heart"),
    SYNAPSE(4, "Synapse"),
    ORIGINAL(5, "Original"),
//    OCTAGON(6, "Octagon");
}

/** SharedPreferenceKey  */
fun PREF() = "pref"
fun TYPE() = "type"
fun LINE_A() = "line_a"
fun LINE_B() = "line_b"
fun BG_COLOR() = "bg_color"

/** IntentKey for ColorPicker  */
fun COLOR_OF_TYPE() = "color_of_type"
fun COLOR() = "color"
