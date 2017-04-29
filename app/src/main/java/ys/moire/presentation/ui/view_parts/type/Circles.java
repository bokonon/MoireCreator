package ys.moire.presentation.ui.view_parts.type;

import android.graphics.Canvas;
import android.util.Log;

import ys.moire.BuildConfig;

/**
 * circle set class.
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
        // check only outside circle.
        if(circle[number-1].x < - circle[number-1].r) {
            // disappear minus x val
            for(int i=0; i< number; i++) {
                circle[i].x = layoutWidth + circle[number-1].r;
            }
        }
        else if(layoutWidth + circle[number-1].r < circle[number-1].x) {
            // disappear plus x value
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
     * add touch value.
     * @param valX dx
     * @param valY dy
     */
    @Override
    public void addTouchVal(final int valX, final int valY) {
        for(int i=0; i< number; i++) {
            circle[i].addTouchVal(valX, valY);
        }
    }
}
