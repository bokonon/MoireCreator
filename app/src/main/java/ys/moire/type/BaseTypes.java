package ys.moire.type;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import ys.moire.util.PreferencesUtil;

/**
 * 図形集合のベースクラス.
 */
public class BaseTypes extends AbstractTypes {

    protected static final int LINE_A = 0;
    protected static final int LINE_B = 1;

    protected static final int MAX_NUMBER = 50;
    /** 自動移動スピード */
    protected static final float AUTO_SPEED = 0.7f;
    /** number */
    protected int number = MAX_NUMBER;
    /** thick */
    protected int thick = 1;
    /** slope */
    protected int slope = 25;
    /** color */
    protected int color = Color.BLACK;
    /** paint */
    protected Paint paint;
    /** タッチ中であるか */
    protected boolean onTouch;

    /** 移動距離（オート）*/
    protected float dx = AUTO_SPEED;

    public BaseTypes() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(thick);
        paint.setColor(color);
    }

    @Override
    public void loadData(final Context context, final String lineConfigName) {
        BaseTypes types = PreferencesUtil.loadTypesData(context, lineConfigName);
        setColor(types.color);
        setNumber(types.number);
        setThick(types.thick);
        setSlope(types.slope);
    }

    @Override
    void init(int whichLine, int layoutWidth, int layoutHeight) {

    }

    @Override
    void checkOutOfRange(int layoutWidth) {

    }

    @Override
    void move(int whichLine) {

    }

    @Override
    void draw(Canvas canvas) {

    }

    /**
     * 色をセットします.
     * @param color 色
     */
    @Override
    public void setColor(int color) {
        this.color = color;
        if(paint == null) {
            paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
        }
        paint.setColor(color);
    }

    /**
     * 色を返します.
     * @return 色
     */
    public int getColor() {
        return color;
    }

    /**
     * 線の数をセットします.
     * @param number 線の数
     */
    @Override
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * 線の数を返します.
     * @return 数
     */
    public int getNumber() {
        return number;
    }

    /**
     * 線の太さをセットします.
     * @param thick 線の太さ
     */
    @Override
    public void setThick(int thick) {
        this.thick = thick;
        if(paint == null) {
            paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
        }
        paint.setStrokeWidth(thick);
    }

    /**
     * 線の太さを返します.
     * @return 線の太さ
     */
    public int getThick() {
        return thick;
    }

    /**
     * 線の傾きをセットします.
     * @param slope 線の傾き
     */
    @Override
    public void setSlope(final int slope) {
        this.slope = slope;
    }

    /**
     * 線の傾きを返します.
     * @return 線の傾き
     */
    public int getSlope() {
        return slope;
    }

    @Override
    void addTouchVal(int valX, int valY) {

    }

    /**
     * タッチ状態をセットします.
     * @param touch タッチ中であるか
     */
    @Override
    public void setOnTouchMode(boolean touch) {
        onTouch = touch;
    }
}
