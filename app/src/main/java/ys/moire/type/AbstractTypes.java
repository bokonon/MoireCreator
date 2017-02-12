package ys.moire.type;

import android.graphics.Canvas;

/**
 * 図形集合の抽象クラス.
 */
public abstract class AbstractTypes {

    abstract void loadData(final BaseTypes types);

    abstract void init(final int whichLine, final int layoutWidth, final int layoutHeight);

    abstract void checkOutOfRange(final int layoutWidth);

    abstract void move(final int whichLine);

    abstract void draw(final Canvas canvas);

    abstract void setColor(final int color);

    abstract void setNumber(final int number);

    abstract void setThick(final int thick);

    abstract void setSlope(final int slope);

    abstract void addTouchVal(final int valX, final int valY);

    abstract void setOnTouchMode(final boolean touch);
}
