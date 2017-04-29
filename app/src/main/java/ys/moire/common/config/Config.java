package ys.moire.common.config;

/**
 * Configクラス.
 */
public class Config {

    /** MoreType */
    public static final int TYPE_LINE = 0;
    public static final int TYPE_CIRCLE = 1;
    public static final int TYPE_RECT = 2;
    public static final int TYPE_ORIGINAL = 3;

    /** SharedPreferenceKey */
    public static final String PREF = "pref";
    public static final String TYPE = "type";
    public static final String LINE_A = "line_a";
    public static final String LINE_B = "line_b";
    public static final String BG_COLOR = "bg_color";

    /** IntentKey for ColorPicker */
    public static final String COLOR_OF_TYPE = "color_of_type";
    public static final String COLOR = "color";

}
