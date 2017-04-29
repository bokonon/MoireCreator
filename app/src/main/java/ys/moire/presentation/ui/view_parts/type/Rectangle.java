package ys.moire.presentation.ui.view_parts.type;

import android.graphics.Path;
import android.graphics.PointF;

/**
 * rectangle class.
 */
public class Rectangle extends BaseType {

    public PointF leftTop;
    public PointF rightTop;
    public PointF rightBottom;
    public PointF leftBottom;

    public float topLength;
    public float bottomLength;

    public Path path;

    public Rectangle() {

    }

    public void init(final float x, final float y, final float height, final float topLength, final float bottomLength) {
        this.topLength = topLength;
        this.bottomLength = bottomLength;
        this.leftTop = new PointF(x - topLength/2, y - height);
        this.rightTop = new PointF(x + topLength/2, y - height);
        this.leftBottom = new PointF(x - bottomLength/2, y + height);
        this.rightBottom = new PointF(x + bottomLength/2, y + height);

        path  = new Path();
        setPath();
    }

    /**
     * scroll rectangle automatically.
     * @param dx dx
     */
    @Override
    void autoMove(final float dx) {
        leftTop.x += dx;
        rightTop.x += dx;
        rightBottom.x += dx;
        leftBottom.x += dx;

        setPath();
    }

    /**
     * move x.
     */
    public void moveX(final float centerX) {
        rightBottom.x = centerX+bottomLength/2;
        leftBottom.x = centerX-bottomLength/2;
        leftTop.x = centerX-topLength/2;
        rightTop.x = centerX+topLength/2;
        setPath();
    }

    /**
     * add touch value.
     * @param touchDx  dx
     * @param touchDy  dy
     */
    public void addTouchVal(final int touchDx, final int touchDy) {
        leftTop.x += touchDx;
        leftTop.y += touchDy;
        rightTop.x += touchDx;
        rightTop.y += touchDy;
        rightBottom.x += touchDx;
        rightBottom.y += touchDy;
        leftBottom.x += touchDx;
        leftBottom.y += touchDy;
        setPath();
    }

    private void setPath() {
        path.reset();
        path.moveTo(leftTop.x, leftTop.y);
        path.lineTo(rightTop.x, rightTop.y);
        path.lineTo(rightBottom.x, rightBottom.y);
        path.lineTo(leftBottom.x, leftBottom.y);
        path.lineTo(leftTop.x, leftTop.y);
    }

}
