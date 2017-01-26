package ys.moire.type;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import ys.moire.BuildConfig;

/**
 * Lineの集合クラス.
 */
public class Lines extends BaseTypes {

    private static final String TAG = "Lines";

    /** line array */
    private Line[] line;

    public Lines() {
        super();
    }

    @Override
    public void init(final int whichLine, final int layoutWidth, final int layoutHeight) {
        line = new Line[number];
        for(int i=0; i< number; i++) {
            line[i] = new Line();
            if(whichLine == LINE_A) {
                line[i].init(layoutHeight, slope, (layoutWidth + slope) / number * i, 0);
            }
            else if(whichLine == LINE_B) {
                line[i].init(layoutHeight, slope, (layoutWidth + slope) / number * i, slope);
            }
        }
    }

    @Override
    public void checkOutOfRange(final int layoutWidth) {
        for(int i=0; i < number; i++) {
            line[i].checkOutOfRange(layoutWidth, slope);
        }
    }

    @Override
    public void move(final int whichLine) {
        if(onTouch) {
            return;
        }
        if(whichLine == LINE_A) {
            for(int i=0; i< number; i++) {
                line[i].autoMove(dx);
            }

        }
        else if(whichLine == LINE_B) {
            for(int j=0; j< number; j++) {
                line[j].autoMove(-dx);
            }
        }
    }

    @Override
    public void draw(final Canvas canvas) {
        for(int i=0; i< number; i++) {
            if(BuildConfig.DEBUG) {
                // Debug用に最初の線を青色に
                if(i == 0) {
                    Paint p = new Paint();
                    p.setStyle(Paint.Style.STROKE);
                    p.setColor(Color.BLUE);
                    p.setStrokeWidth(thick);
                    canvas.drawLine(line[i].topX, line[i].topY, line[i].bottomX,
                            line[i].bottomY, p);
                    continue;
                }
                // Debug用に最後の線を赤色に
                if(i == number-1) {
                    Paint p = new Paint();
                    p.setStyle(Paint.Style.STROKE);
                    p.setColor(Color.RED);
                    p.setStrokeWidth(thick);
                    canvas.drawLine(line[i].topX, line[i].topY, line[i].bottomX,
                            line[i].bottomY, p);
                    continue;
                }
            }
            canvas.drawLine(line[i].topX, line[i].topY, line[i].bottomX,
                    line[i].bottomY, paint);
        }
    }

    /**
     * タッチ移動の値をLineに反映します.
     * @param valX X移動値
     */
    @Override
    public void addTouchVal(final int valX, final int valY) {
        for(int i=0; i< number; i++) {
            line[i].addTouchVal(valX, 0);
        }
    }

}
