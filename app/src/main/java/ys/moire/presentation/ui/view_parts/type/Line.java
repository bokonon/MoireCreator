package ys.moire.presentation.ui.view_parts.type;

/**
 * line class.
 */
public class Line extends BaseType {
    public float topX;
    public float topY;
    public float bottomX;
    public float bottomY;

    public Line() {

    }

    /**
     * init line.
     * @param layoutHeight screen width
     * @param slope slope
     * @param val offset x
     * @param arg b slope value (A:0 B:slope)
     */
    public void init(final int layoutHeight, final int slope, final int val, final int arg) {
        topX = val + slope - arg;
        topY = 0;
        bottomX = topX - slope + arg*2;
        bottomY = layoutHeight;
    }

    /**
     * scroll line automatically.
     * @param dx dx
     */
    @Override
    void autoMove(float dx) {
        topX += dx;
        bottomX += dx;
    }

    /**
     * check if the line is out of range.
     * @param layoutWidth 画面の横幅
     */
    @Override
    void checkOutOfRange(final int layoutWidth, final int slope) {
        // LineA
        if(bottomX < topX) {
            if(layoutWidth < bottomX) {
                float diff = bottomX - layoutWidth;
                topX = diff;
                bottomX = topX - slope;
            }
            else if(topX < 0) {
                float diff = - topX;
                topX = layoutWidth + slope - diff;
                bottomX = topX - slope;
            }
        }
        // LineB
        else {
            if(topX < -slope) {
                float diff = - slope - topX;
                topX = layoutWidth - diff;
                bottomX = topX + slope;
            }
            else if(layoutWidth < topX) {
                float diff = topX - layoutWidth;
                topX = -slope + diff;
                bottomX = topX + slope;
            }
        }
    }

    /**
     * タッチ移動の値を反映します.
     * @param touchDx  x座標の移動値
     * @param touchDy  y座標の移動値
     */
    public void addTouchVal(final int touchDx, final int touchDy) {
        topX += touchDx;
        bottomX += touchDx;
        topY += touchDy;
        bottomY += touchDy;
    }

}
