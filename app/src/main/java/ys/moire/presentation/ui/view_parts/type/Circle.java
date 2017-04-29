package ys.moire.presentation.ui.view_parts.type;

/**
 * Circle class.
 */
public class Circle extends BaseType {
    public float x;
    public float y;
    /** radius */
    public float r;

    public Circle() {

    }

    public void init(final float x, final float y, final float r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }

    /**
     * scroll circle automatically.
     * @param dx auto move distance
     */
    @Override
    void autoMove(float dx) {
        x += dx;
    }

    /**
     * add touch value.
     * @param touchDx  dx
     * @param touchDy  dy
     */
    public void addTouchVal(final int touchDx, final int touchDy) {
        x += touchDx;
        y += touchDy;
    }

}
