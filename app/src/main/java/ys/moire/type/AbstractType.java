package ys.moire.type;

/**
 * 図形のベースクラス.
 */
public abstract class AbstractType {

    abstract void autoMove(final float dx);

    abstract void checkOutOfRange(final int layoutWidth, final int slope);

    abstract void addTouchVal(final int touchDx, final int touchDy);
}
