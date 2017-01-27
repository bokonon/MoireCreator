package ys.moire.type;

/**
 * Circleクラス.
 */
public class Circle extends BaseType {
    public float x;
    public float y;
    /** 半径 */
    public float r;

    public Circle() {

    }

    public void init(final float x, final float y, final float r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }

    /**
     * Circleを自動でスクロールさせます.
     * @param dx 自動で移動する距離
     */
    @Override
    void autoMove(float dx) {
        x += dx;
    }

    /**
     * タッチ移動の値を反映します.
     * @param touchDx  x座標の移動値
     * @param touchDy  y座標の移動値
     */
    public void addTouchVal(final int touchDx, final int touchDy) {
        x += touchDx;
        y += touchDy;
    }

}
