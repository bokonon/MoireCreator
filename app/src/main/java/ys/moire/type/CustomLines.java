package ys.moire.type;

import android.graphics.Canvas;

/**
 * オリジナルラインの集合クラス.
 */
public class CustomLines extends BaseTypes {

    private static final String TAG = "CustomLines";

    /** CustomLine array */
    private CustomLine[] customLines;

    public CustomLines() {
        super();
    }

    public void init(final int whichLine,
                     final int layoutWidth,
                     final int layoutHeight) {
        customLines = new CustomLine[number];
        for(int i=0; i< number; i++) {
            customLines[i] = new CustomLine();
        }
    }

    public void checkOutOfRange(final int whichLine, final int layoutWidth) {
        if(onTouch) {
            return;
        }
        for(int i=0; i < number; i++) {
            customLines[i].checkOutOfRange(whichLine, layoutWidth, slope);
        }
    }

    @Override
    public void move(final int whichLine) {
        if(onTouch) {
            return;
        }
        if(whichLine == LINE_A) {
            for(int i=0; i< number; i++) {
                customLines[i].autoMove(dx);
            }
        }
        else if(whichLine == LINE_B) {
            for(int j=0; j< number; j++) {
                customLines[j].autoMove(-dx);
            }
        }
    }

    @Override
    public void draw(final Canvas canvas) {
        for (int i = 0; i < number; i++) {
            canvas.drawPath(customLines[i].path, paint);
        }
    }

    /**
     * タッチ移動の値をLineに反映します.
     * @param layoutWidth width
     * @param valX X移動値
     * @param valY Y移動値
     * @param moveCount onTouchMoveされた回数
     */
    public void drawOriginalLine(final int layoutWidth, final float valX, final float valY, final int moveCount) {
        for(int i=0; i< number; i++) {
            customLines[i].drawOriginalLine(valX-layoutWidth/2f+(i+1)*layoutWidth/number, valY, moveCount);
        }
    }
}
