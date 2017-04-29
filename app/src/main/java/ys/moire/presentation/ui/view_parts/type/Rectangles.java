package ys.moire.presentation.ui.view_parts.type;

import android.graphics.Canvas;

/**
 * rectangle set class.
 */
public class Rectangles extends BaseTypes {

    private static final String TAG = "Rectangles";

    /** Rectangle array */
    private Rectangle[] rectangle;

    // Trapezoidal judgment
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
                // The upper side and the lower side are provisional values
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
        // check only out side line
        if(isTrapezoid) {
            if(rectangle[number-1].rightBottom.x < 0) {
                // disappear less than 0
                for(int i=0; i< number; i++) {
                    rectangle[i].moveX(layoutWidth+rectangle[number - 1].bottomLength / 2);
                }
            }
            else if(layoutWidth < rectangle[number-1].leftBottom.x) {
                // disappear more than width
                for(int i=0; i< number; i++) {
                    rectangle[i].moveX(-rectangle[number - 1].bottomLength / 2);
                }
            }
        }
        else {
            if(rectangle[number-1].rightTop.x < 0) {
                // disappear less than 0
                for(int i=0; i< number; i++) {
                    rectangle[i].moveX(layoutWidth+rectangle[number-1].topLength/2);
                }
            }
            else if(layoutWidth < rectangle[number-1].leftTop.x) {
                // disappear more than width
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
     * add touch value.
     * @param valX dx
     */
    @Override
    public void addTouchVal(final int valX, final int valY) {
        for(int i=0; i< number; i++) {
            rectangle[i].addTouchVal(valX, valY);
        }
    }
}
