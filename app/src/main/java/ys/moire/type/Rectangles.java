package ys.moire.type;

import android.graphics.Canvas;

/**
 * 四角形の集合クラス.
 */
public class Rectangles extends BaseTypes {

    private static final String TAG = "Rectangles";

    /** Rectangle array */
    private Rectangle[] rectangle;

    // 台形か？
    private boolean isTrapezoid;

    public Rectangles() {
        super();
    }

    public void init(final int whichLine,
                     final int layoutWidth,
                     final int layoutHeight,
                     final float maxTopLength,
                     final float maxBottomLength) {
        rectangle = new Rectangle[number];
        for(int i=0; i< number; i++) {
            rectangle[i] = new Rectangle();
            if(whichLine == LINE_A) {
                // 上辺と下辺は暫定値
                rectangle[i].init(0, layoutHeight/3f,
                        layoutHeight/3f/number*i,
                        maxTopLength/number*i,
                        maxBottomLength/number*i);
            }
            else if(whichLine == LINE_B) {
                rectangle[i].init(layoutWidth, layoutHeight*(2/3f),
                        layoutHeight/3f/number*i,
                        maxTopLength/number*i,
                        maxBottomLength/number*i);
            }
        }
        if(maxTopLength <= maxBottomLength) {
            isTrapezoid = true;
        }
        else {
            isTrapezoid = false;
        }
    }

    @Override
    public void checkOutOfRange(final int layoutWidth) {
        //  ここでは大外の方形についてのチェックのみを行います
        if(isTrapezoid) {
            if(rectangle[number-1].rightBottom.x < 0) {
                // x座標マイナス方面に消失
                for(int i=0; i< number; i++) {
                    rectangle[i].moveX(layoutWidth+rectangle[number - 1].bottomLength / 2);
                }
            }
            else if(layoutWidth < rectangle[number-1].leftBottom.x) {
                // x座標プラス方面に消失
                for(int i=0; i< number; i++) {
                    rectangle[i].moveX(-rectangle[number - 1].bottomLength / 2);
                }
            }
        }
        else {
            if(rectangle[number-1].rightTop.x < 0) {
                // x座標マイナス方面に消失
                for(int i=0; i< number; i++) {
                    rectangle[i].moveX(layoutWidth+rectangle[number-1].topLength/2);
                }
            }
            else if(layoutWidth < rectangle[number-1].leftTop.x) {
                // x座標プラス方面に消失
                for(int i=0; i< number; i++) {
                    rectangle[i].moveX(-rectangle[number-1].topLength/2);
                }
            }
        }
    }

    @Override
    public void move(final int whichLine) {
        if(onTouch) {
            return;
        }
        if(whichLine == LINE_A) {
            for(int i=0; i< number; i++) {
                rectangle[i].autoMove(dx);
            }
        }
        else if(whichLine == LINE_B) {
            for(int j=0; j< number; j++) {
                rectangle[j].autoMove(-dx);
            }
        }
    }

    @Override
    public void draw(final Canvas canvas) {
        for (int i = 0; i < number; i++) {
            canvas.drawPath(rectangle[i].path, paint);
        }
    }

    /**
     * タッチ移動の値をLineに反映します.
     * @param valX X移動値
     */
    @Override
    public void addTouchVal(final int valX, final int valY) {
        for(int i=0; i< number; i++) {
            rectangle[i].addTouchVal(valX, valY);
        }
    }
}
