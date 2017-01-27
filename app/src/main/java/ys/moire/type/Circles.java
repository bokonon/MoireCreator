package ys.moire.type;

import android.graphics.Canvas;
import android.util.Log;

import ys.moire.BuildConfig;

/**
 * 円図形の集合クラス.
 */
public class Circles extends BaseTypes {

    private static final String TAG = "Circles";

    /** Circle array */
    private Circle[] circle;

    public Circles() {
        super();
    }

    @Override
    public void init(final int whichLine, final int layoutWidth, final int layoutHeight) {
        if(BuildConfig.DEBUG) {
            Log.d(TAG, "Circles layoutHeight : "+layoutHeight);
        }
        circle = new Circle[number];
        for(int i=0; i< number; i++) {
            circle[i] = new Circle();
            if(whichLine == LINE_A) {
                circle[i].init(0, layoutHeight/3f, layoutHeight/3f/number*i+4);
            }
            else if(whichLine == LINE_B) {
                circle[i].init(layoutWidth, layoutHeight*(2/3f), layoutHeight/3f/number*i+4);
            }
        }
    }

    @Override
    public void checkOutOfRange(final int layoutWidth) {
        //  ここでは大外の円についてのチェックのみを行います
        if(circle[number-1].x < - circle[number-1].r) {
            // x座標マイナス方面に消失
            for(int i=0; i< number; i++) {
                circle[i].x = layoutWidth + circle[number-1].r;
            }
        }
        else if(layoutWidth + circle[number-1].r < circle[number-1].x) {
            // x座標プラス方面に消失
            for(int i=0; i< number; i++) {
                circle[i].x = - circle[number-1].r;
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
                circle[i].autoMove(dx);
            }

        }
        else if(whichLine == LINE_B) {
            for(int j=0; j< number; j++) {
                circle[j].autoMove(-dx);
            }
        }
    }

    @Override
    public void draw(final Canvas canvas) {
        for (int i = 0; i < number; i++) {
            canvas.drawCircle(circle[i].x, circle[i].y, circle[i].r, paint);
        }
    }

    /**
     * タッチ移動の値をLineに反映します.
     * @param valX X移動値
     */
    @Override
    public void addTouchVal(final int valX, final int valY) {
        for(int i=0; i< number; i++) {
            circle[i].addTouchVal(valX, valY);
        }
    }
}
