package ys.moire.type;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Geometry base class.
 */
public class BaseTypes extends AbstractTypes {

    protected static final int LINE_A = 0;
    protected static final int LINE_B = 1;

    protected static final int MAX_NUMBER = 50;
    /** auto move speed */
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
    /** isOnTouch */
    protected boolean onTouch;

    /** move value */
    protected float dx = AUTO_SPEED;

    public BaseTypes() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(thick);
        paint.setColor(color);
    }

    @Override
    public void loadData(final BaseTypes types) {
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
     * set color.
     * @param color color
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
     * get color.
     * @return color
     */
    public int getColor() {
        return color;
    }

    /**
     * set line num.
     * @param number line num
     */
    @Override
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * get line num.
     * @return line num
     */
    public int getNumber() {
        return number;
    }

    /**
     * set line thick.
     * @param thick line thick
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
     * get line thick.
     * @return line thick
     */
    public int getThick() {
        return thick;
    }

    /**
     * set line slope.
     * @param slope line slope
     */
    @Override
    public void setSlope(final int slope) {
        this.slope = slope;
    }

    /**
     * get line slope.
     * @return slope value
     */
    public int getSlope() {
        return slope;
    }

    @Override
    void addTouchVal(int valX, int valY) {

    }

    /**
     * set is OnTouch.
     * @param touch isOnTouch
     */
    @Override
    public void setOnTouchMode(boolean touch) {
        onTouch = touch;
    }
}
