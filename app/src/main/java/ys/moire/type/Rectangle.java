package ys.moire.type;

import android.graphics.Path;
import android.graphics.PointF;

/**
 * Rectangleクラス.
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
     * Rectangleを自動でスクロールさせます.
     * @param dx 自動で移動する距離
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
     * x座標を移動させます.
     */
    public void moveX(final float centerX) {
        rightBottom.x = centerX+bottomLength/2;
        leftBottom.x = centerX-bottomLength/2;
        leftTop.x = centerX-topLength/2;
        rightTop.x = centerX+topLength/2;
        setPath();
    }

    /**
     * タッチ移動の値を反映します.
     * @param touchDx  x座標の移動値
     * @param touchDy  y座標の移動値
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
